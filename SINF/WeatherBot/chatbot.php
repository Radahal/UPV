<?php

error_reporting(0);


class Event {
	public $title;
	public $description;
	public $date;
	public $url;
}

class Weather {
	public $time;
	public $description;
	public $temperature;
	public $humidity;
	public $clouds;
	public $wind;
	public $rain;
}

class User_pref {
	public $temp_min;
	public $temp_max;
	public $sun;
	public $wind;
	public $rain;
}


global $token_event; // = "&token=W5MLNKJO4YB7XGQG2M6D";
global $token_weather; // = "&APPID=41ee72cd91a1690498049312bd61451f";
global $distance; //= "?location.within=3km";
global $valLat;// = "39.478097";
global $labLat; //= "&location.latitude=".$valLat;
global $valLon; //= "-0.338406";
global $labLon; //= "&location.longitude=".$valLon;


switch ($_GET['function'])
{

	case 'allEvents':
		allEvents();
		break;
	case 'eventsByCategory':
		eventsByCategory();
		break;
	case 'forecastToday':
		forecastToday();
		break;
	case 'forecastTommorow':
		forecastTommorow();
		break;
}

function allEvents() {
	
	$token_event = "&token=W5MLNKJO4YB7XGQG2M6D";
	$distance = "?location.within=3km";
	$valLat = "39.478097";
	$labLat = "&location.latitude=".$valLat;
	$valLon = "-0.338406";
	$labLon = "&location.longitude=".$valLon;

	if(isset($_GET['lat'])) {
		setLocalization($_GET['lat'], $_GET['lon']);
	}


	//https://www.eventbriteapi.com/v3/events/search/.$distance.$labLat.$labLon.$token_event
	$url = "https://www.eventbriteapi.com/v3/events/search/".$distance.$labLat.$labLon.$token_event;
	$content  = file_get_contents($url);
	$result = json_decode($content);

	$events = array();
	$events_len = sizeof($result->events);

	if($events_len>0) {

		for($i=0; ($i<10) and ($i<$events_len); $i++) {
			$event_title = $result->events[$i]->name->text;
			//$event_description = $result->events[$i]->description->text;
			$event_url = $result->events[$i]->url;
			$event_start = $result->events[$i]->start->local;
			$event_end = $result->events[$i]->end->local;

			$title = "*Title:* ".$event_title."\n\n";
			//$description = "*Description:* ".$event_description."\n\n";
			$date = "*Date:*\n\t*Start:* ".$event_start."\n\t*End:* ".$event_end."\n\n";
			$date = str_replace("T", " ",$date);
			$url = "*Source:* ".$event_url."\n\n";

			$event = new Event();
			$event->title = $title;
			//$event->description = $description;
			$event->date = $date;
			$event->url = $url;

			array_push($events, $event);
		}

		$json = json_encode(listEvents($events));

		echo $json;
	} else {

		$json = json_encode(defaultMsg());
		echo $json;
	}
	
}


function eventsByCategory() {
	
	$token_event = "&token=W5MLNKJO4YB7XGQG2M6D";
	$distance = "?location.within=3km";
	$valLat = "39.478097";
	$labLat = "&location.latitude=".$valLat;
	$valLon = "-0.338406";
	$labLon = "&location.longitude=".$valLon;
	$category_id = "0";
	$category = "";
	if(isset($_GET['cat_id'])) {
		$category_id = $_GET['cat_id'];
		$category = "&categories=".$category_id;
	}
	
	if(isset($_GET['lat'])) {
		setLocalization($_GET['lat'], $_GET['lon']);
	}
	
	
	//https://www.eventbriteapi.com/v3/events/search/.$distance.$labLat.$labLon.$token_event
	$url = "https://www.eventbriteapi.com/v3/events/search/".$distance.$labLat.$labLon.$category.$token_event;
	$content  = file_get_contents($url);
	$result = json_decode($content);
	
	$events = array();
	$events_len = sizeof($result->events);
	
	if($events_len>0) {
	
		for($i=0; ($i<10) and ($i<$events_len); $i++) {
			$event_title = $result->events[$i]->name->text;
			//$event_description = $result->events[$i]->description->text;
			$event_url = $result->events[$i]->url;
			$event_start = $result->events[$i]->start->local;
			$event_end = $result->events[$i]->end->local;

			$title = "*Title:* ".$event_title."\n\n";
			//$description = "*Description:* ".$event_description."\n\n";
			$date = "*Date:*\n\t*Start:* ".$event_start."\n\t*End:* ".$event_end."\n\n";
			$date = str_replace("T", " ",$date);
			$url = "*Source:* ".$event_url."\n\n";

			$event = new Event();
			$event->title = $title;
			//$event->description = $description;
			$event->date = $date;
			$event->url = $url;

			array_push($events, $event);
		}

		$json = json_encode(listEvents($events));

		echo $json;
	} else {
		
		$json = json_encode(defaultMsg());
		echo $json;
	}
}


function forecastToday() {
	// http://openweathermap.org/data/2.5/forecast?lat=39.478097&lon=-0.338406&appid=b6907d289e10d714a6e88b30761fae22
	// indices 0-7
	
	$token_weather = "&appid=b6907d289e10d714a6e88b30761fae22";
	$valLat = "39.478097";
	$labLat = "&location.latitude=".$valLat;
	$valLon = "-0.338406";
	$labLon = "&location.longitude=".$valLon;
	$user_pref = new User_pref();
	
	if(isset($_GET['min_temp'])) {
		$user_min = $_GET['min_temp'];
		$user_max = $_GET['max_temp'];
		$user_wind = $_GET['wind'];
		$user_rain = $_GET['rain'];
		$user_sun = $_GET['sun'];
		
		$user_pref->temp_min = $user_min;
		$user_pref->temp_max = $user_max;
		$user_pref->wind = $user_wind;
		$user_pref->rain = $user_rain;
		$user_pref->sun = $user_sun;
	}
	
	if(isset($_GET['lat'])) {
		setLocalization($_GET['lat'], $_GET['lon']);
	}
	
	$url = "http://openweathermap.org/data/2.5/forecast?".$labLat.$labLon.$token_weather;
	$content  = file_get_contents($url);
	$result = json_decode($content);
	
	$conditions = array();
	
	for($i=0; $i<8; $i++) {
	
		$time = $result->list[$i]->dt_txt;
		$temp = $result->list[$i]->main->temp;
		$description = $result->list[$i]->weather[0]->main;
		$humidity = $result->list[$i]->main->humidity;
		$clouds = $result->list[$i]->clouds->all;
		$wind = $result->list[$i]->wind->speed;
		//$rain = $result->list[$i]->rain->3h;
		
		$weather = new Weather();
		$weather->time = $time;
		$weather->temperature = $temp;
		$weather->description = $description;
		$weather->humidity = $humidity;
		$weather->clouds = $clouds;
		$weather->wind = $wind;
		//$weather->rain = $rain;
		
		array_push($conditions, $weather);
	}
	
	$json = json_encode(listForecast($conditions, $user_pref));

	echo $json;
	
}


function forecastTommorow() {
	// http://openweathermap.org/data/2.5/forecast?lat=39.478097&lon=-0.338406&appid=b6907d289e10d714a6e88b30761fae22
	// indices 8-15
	
	$token_weather = "&appid=b6907d289e10d714a6e88b30761fae22";
	$valLat = "39.478097";
	$labLat = "&location.latitude=".$valLat;
	$valLon = "-0.338406";
	$labLon = "&location.longitude=".$valLon;
	$user_pref = new User_pref();
	
	if(isset($_GET['min_temp'])) {
		$user_min = $_GET['min_temp'];
		$user_max = $_GET['max_temp'];
		$user_wind = $_GET['wind'];
		$user_rain = $_GET['rain'];
		$user_sun = $_GET['sun'];
		

		$user_pref->temp_min = $user_min;
		$user_pref->temp_max = $user_max;
		$user_pref->wind = $user_wind;
		$user_pref->rain = $user_rain;
		$user_pref->sun = $user_sun;
	}

	if(isset($_GET['lat'])) {
		setLocalization($_GET['lat'], $_GET['lon']);
	}

	$url = "http://openweathermap.org/data/2.5/forecast?".$labLat.$labLon.$token_weather;
	$content  = file_get_contents($url);
	$result = json_decode($content);

	$conditions = array();

	for($i=8; $i<16; $i++) {

		$time = $result->list[$i]->dt_txt;
		$temp = $result->list[$i]->main->temp;
		$description = $result->list[$i]->weather[0]->main;
		$humidity = $result->list[$i]->main->humidity;
		$clouds = $result->list[$i]->clouds->all;
		$wind = $result->list[$i]->wind->speed;
		//$rain = $result->list[$i]->rain->3h;

		$weather = new Weather();
		$weather->time = $time;
		$weather->temperature = $temp;
		$weather->description = $description;
		$weather->humidity = $humidity;
		$weather->clouds = $clouds;
		$weather->wind = $wind;
		//$weather->rain = $rain;

		array_push($conditions, $weather);
	}

	$json = json_encode(listForecast($conditions, $user_pref));

	echo $json;
	
}

// JSON item

function qrBtn($title, $blockname)
{
	$data = array
		(
		'title' => $title,
		'block_names' => array($blockname)
	);
	return $data;
}

// JSON item

function quickReply($t, $arrBtns)
{
	$data = array
		(
		'text' => $t,
		'quick_replies' => $arrBtns
	);
	return $data;
}

// JSON item
function listMsg($arrMsgs) {
	$messages = array();
	for($i=0; $i < sizeof($arrMsgs); $i++ ) {
		$msg = array(
			'text' => $arrMsgs[$i]
		);
		array_push($messages, $msg);
	}
	return $messages;
}

// JSON item
function defaultMsg() {
	$messages = array();
	$msg = array(
		'text' => "I was looking for any information based on your request, but there is no informations. Try with another option."
	);
	array_push($messages, $msg);
	return $messages;
}

function listEvents($arrEvents) {
	$messages = array();
	for($i=0; $i < sizeof($arrEvents); $i++ ) {
		
		$title = $arrEvents[$i]->title;
		//$description = $arrEvents[$i]->description;
		$date = $arrEvents[$i]->date;
		$url = $arrEvents[$i]->url;
		
		//$msg_text = $title.$description.$date.$url;
		$msg_text = $title.$date.$url;
		
		$msg = array(
			'text' => $msg_text //$arrEvents[$i]->title.$arrEvents[$i]->description.$arrEvents[$i]->date.$arrEvents[$i]->url
		);
		array_push($messages, $msg);
	}
	return $messages;
}


function listForecast($arr, $user_pref) {
	$messages = array();
	
	$flag_min = false;
	$flag_max = false;
	$flag_rain = false;
	$flag_wind = false;
	$flag_sun = false;
	
	for($i=0; $i < sizeof($arr); $i++ ) {

		$time = "*Time:* ".$arr[$i]->time."\n";
		$description = "\tGeneral: ".$arr[$i]->description."\n";
		$temp = "\tTemparature: ".$arr[$i]->temperature."\n";
		$wind = "\tWind speed: ".$arr[$i]->wind."\n";
		//$rain = "\tRain: ".$arr[$i]->rain."\n";
		$clouds = "\tCloudiness: ".$arr[$i]->clouds."\n";
		$humidity = "\tHumidity: ".$arr[$i]->humidity."\n";
		
		if(intval($temp)>$user_pref->temp_max)
			$flag_max=true;
		
		if(intval($temp)<$user_pref->temp_max)
			$flag_min=true;
		
		if((intval($wind)>10) && ($user_pref->wind=="Yes"))
			$flag_wind=true;
		   
		if((intval($clouds)<20) && ($user_pref->sun=="Yes"))
		   $flag_sun=true; 
		
		//$msg_text = $time.$description.$temp.$wind.$rain.$clouds.$humidity;
		$msg_text = $time.$description.$temp.$wind.$clouds.$humidity;

		$msg = array(
			'text' => $msg_text 
		);
		array_push($messages, $msg);
	}
	
	if($flag_max || $flag_min || $flag_rain || $flag_sun || $flag_wind) {
		$msg_text="During that day:\n";
		if($flag_max) 
			$msg_text=$msg_text."\t- the temperature might be higher that you normally prefere,\n";
		if($flag_min) 
			$msg_text=$msg_text."\t- the temperature might be lower that you normally prefere,\n";
		if($flag_wind) 
			$msg_text=$msg_text."\t- the wind be strong,\n";
		if($flag_sun) 
			$msg_text=$msg_text."\t- will be sunny";


		$msg = array (
			'text' => $msg_text
		);	

		array_push($messages, $msg);	
	}
	return $messages;
}



function listGallery($arr) {
	
	$elements = array();
	$url_eventbrite = "https://www.eventbrite.com/";
	$url_image = "https://rockets.chatfuel.com/assets/shirt.jpg";
	
	for($i=0; $i < sizeof($arr); $i++) {
		
		$buttons = array();
		$btn = array(
			'type'=>"web_url",
			'url'=>$url_eventbrite,
			'title'=>"See more"
		);
		array_push($buttons, $btn);
		$element = array(
			'title' => $arr[$i]->title,
			'image_url' => $url_image,
			'subtitle' => $arr[$i]->subtitle,
			'buttons' => $buttons
		);
		array_push($elements, $element);
	}
	
	$gallery = array(
		'attachment'=> array (
			'type'=>'template',
			'payload'=> array(
				'template_type'=>'generic',
				'image_aspect_ratio'=>'square',
				'elements'=>$elements
			)
		)
	);
	
	
	return $gallery;
}



function setLocalization($newLat, $newLon) {
	$valLat = $newLat;
	$valLon = $newLon;
	$labLat = "&location.latitude=".$valLat;
	$labLon = "&location.longitude=".$valLon;
}

?>
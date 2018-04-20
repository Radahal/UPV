/*  
*   ------------------------------------------------------------------------------
*								
*		SPACE INVADERS
*
*		Rafa³ Gosiewski
*		Valencia, 2018
*		Final project for course AEV at Universitat Politecnica de Valencia
*
*	------------------------------------------------------------------------------
*/
#include <nds.h>
#include <stdio.h>
#include <screens.h>
#include <math.h>

#define BOARD_WIDTH 256
#define BOARD_HEIGHT 192

//sprites.h
#include <gfx_ship.h>
#include <gfx_invader1.h>
#include <gfx_invader2.h>
#include <gfx_invader3.h>
#include <gfx_mystery.h>
#include <gfx_fire1.h>
#include <gfx_fire2.h>
#include <gfx_fire3.h>

#define tiles_ship 0
#define tiles_invader1 1
#define tiles_invader2 2
#define tiles_invader3 3
#define tiles_fire1 4
#define tiles_fire2 5
#define tiles_fire3 6
#define tiles_mystery 7

#define pal_ship 0
#define pal_invader1 1
#define pal_invader2 2
#define pal_invader3 3
#define pal_fire1 4
#define pal_fire2 5
#define pal_fire3 6
#define pal_mystery 7

typedef struct t_spriteEntry
{
	u16 attr0;			// position y (board size - sprite size)
	u16 attr1;			// position x (board size - sprite size) + attr1_size_16;
	u16 attr2;			// tile and pallete (tile + (pal << 12))
	u16 affine_data;	//
} spriteAttrEntry;

#define sprites ((spriteAttrEntry*)OAM)
//sprites[0] - ship
//sprites[1-55] - invaders
//sprites[56-58] - lives
//sprites[59-62] - shoots
//sprites[63] - mystery

//Bullet.h
typedef struct {
	int id;
	int type;
	int x;
	int y;
	int dy;
	int state;
} Bullet;

Bullet bullets[] = {
	{59,0,0,0,0,0},		//player shoot
	{60,0,0,0,0,0},		//1st invader shoot
	{61,0,0,0,0,0},		//2st invader shoot
	{62,0,0,0,0,0}			//3st invader shoot
};


//Graphics.h
#define tile2bgram(t) (BG_GFX + (t) *16)
#define pal2bgram(p) (BG_PALETTE + (p) * 16)
#define tile2objram(t) (SPRITE_GFX + (t) * 16)
#define pal2objram(p) (SPRITE_PALETTE + (p) * 16)
#define tile4(t) ((t) * 4)
#define backdrop_colour RGB8( 0, 0, 0 )

//Player.h
#define MAX_LIFES 3
#define MAX_P_SHOOTS 1
#define P_Y_POSITION 35
#define P_WIDTH 8
#define BOARD_WIDTH 256
#define BOARD_HEIGHT 192

struct Player {
	int lifes;
	int x;
	int y;
	int width;
	int height;
	int moving;
	int id;
	Bullet* bullet;
};

struct Player ship;

//Invader.h

#define MAX_I_SHOOTS 3
#define BULLET_I_CHANCE 10
#define MAX_HOR_MOVES 35
#define MAX_VER_MOVES 25

typedef struct {
	int id;
	int type;
	int x;
	int y;
	int dy;
	int shoot;
	int movement;
	int state;
} Invader;

struct Mystery {
	int id;
	int type;
	int x;
	int y;
	int state;
	int value;
};

struct Mystery mystery;

int invaderMoves = 0;
int invadersDirection = 1;
int invadersBullets = 0;
int killedInvaders = 0;

Invader invaders[] = {
		{1, 0, 7, 15, 0, 0, 0,1 },
		{2, 0, 26, 15, 0, 0, 0,1 },
		{3, 0, 45, 15, 0, 0, 0,1 },
		{4, 0, 64, 15, 0, 0, 0,1 },
		{5, 0, 83, 15, 0, 0, 0,1 },
		{6, 0, 102, 15, 0, 0, 0,1 },
		{7, 0, 121, 15, 0, 0, 0,1 },
		{8, 0, 140, 15, 0, 0, 0,1 },
		{9, 0, 159, 15, 0, 0, 0,1 },
		{10, 0, 178, 15, 0, 0, 0,1 },
		{11, 0, 197, 15, 0, 0, 0,1 },
		
		{12, 0, 7, 34, 0, 0, 0,1 },
		{13, 0, 26, 34, 0, 0, 0,1 },
		{14, 0, 45, 34, 0, 0, 0,1 },
		{15, 0, 64, 34, 0, 0, 0,1 },
		{16, 0, 83, 34, 0, 0, 0,1 },
		{17, 0, 102, 34, 0, 0, 0,1 },
		{18, 0, 121, 34, 0, 0, 0,1 },
		{19, 0, 140, 34, 0, 0, 0,1 },
		{20, 0, 159, 34, 0, 0, 0,1 },
		{21, 0, 178, 34, 0, 0, 0,1 },
		{22, 0, 197, 34, 0, 0, 0,1 },
		
		{23, 1, 7, 53, 0, 0, 0,1 },
		{24, 1, 26, 53, 0, 0, 0,1 },
		{25, 1, 45, 53, 0, 0, 0,1 },
		{26, 1, 64, 53, 0, 0, 0,1 },
		{27, 1, 83, 53, 0, 0, 0,1 },
		{28, 1, 102, 53, 0, 0, 0,1 },
		{29, 1, 121, 53, 0, 0, 0,1 },
		{30, 1, 140, 53, 0, 0, 0,1 },
		{31, 1, 159, 53, 0, 0, 0,1 },
		{32, 1, 178, 53, 0, 0, 0,1 },
		{33, 1, 197, 53, 0, 0, 0,1 },
		
		{34, 1, 7, 72, 0, 0, 0,1 },
		{35, 1, 26, 72, 0, 0, 0,1 },
		{36, 1, 45, 72, 0, 0, 0,1 },
		{37, 1, 64, 72, 0, 0, 0,1 },
		{38, 1, 83, 72, 0, 0, 0,1 },
		{39, 1, 102, 72, 0, 0, 0,1 },
		{40, 1, 121, 72, 0, 0, 0,1 },
		{41, 1, 140, 72, 0, 0, 0,1 },
		{42, 1, 159, 72, 0, 0, 0,1 },
		{43, 1, 178, 72, 0, 0, 0,1 },
		{44, 1, 197, 72, 0, 0, 0,1 },
		
		{45, 2, 7, 91, 0, 0, 0,1 },
		{46, 2, 26, 91, 0, 0, 0,1 },
		{47, 2, 45, 91, 0, 0, 0,1 },
		{48, 2, 64, 91, 0, 0, 0,1 },
		{49, 2, 83, 91, 0, 0, 0,1 },
		{50, 2, 102, 91, 0, 0, 0,1 },
		{51, 2, 121, 91, 0, 0, 0,1 },
		{52, 2, 140, 91, 0, 0, 0,1 },
		{53, 2, 159, 91, 0, 0, 0,1 },
		{54, 2, 178, 91, 0, 0, 0,1 },
		{55, 2, 197, 91, 0, 0, 0,1 }
		
	};

//Timers.h
int invaderTimer_id = 1;
int invaderTimer_vel = 3;
int bulletTimer_id = 2;
int bulletTimer_vel = 80;
int soundsTimer_id = 3;
int soundsTimer_vel = 30;

//Sounds.h
int channel0 = 0;	//invaders moves
int channel1 = 0;	//bullet sound
int channel2a = 0;	//mystery noise
int channel2b = 0;	//mystery sound
int channel3a = 0;	//explosion
int channel3b = 0;	//explosion
bool invadersSounds = false;
bool bulletSounds = false;
bool mysterySounds = false;
bool explosionSounds = false;

int explosionFreq = 1100;
int explosionNoiseFreq = 100;
int invaderFreq = 200;
int mysteryNoiseFreq = 2500;
int mysterySoundFreq = 400;
int bulletFreq = 8000;


//functions definition for main.c
void init();
void initFast();
void initGame();
void readKeys();
void waitForA();
void checkForGameOver();
void gameOver();
void checkForRefresh();
void refreshMap();
void waitForRefreshGame() ;
void initSounds();

//Graphics.c
void setupGraphics();
void updateGraphics();
void refreshLifes();
void restoreLifes();

//Sprites.c
void initSpriteObjects();

//Bullet.c
int getBulletWidth(int);
int getBulletHeight(int);
void removeBullets();
void killBulletById(int id);

//Player.c
void player_moveL();
void player_moveR();
void player_fire();
void initPlayer();
void updatePlayerBullet();
void killPlayer();
void centerPlayer();

//Invader.c
void updateInvaders();
void updateInvaderBullets();
void getInvaderSize(int, int*, int*);
int checkCollisionWithInvaders(int x, int y);
int checkCollisionWithMystery(int x, int y);
int checkCollisionWithBullets(int x, int y);
int getInvaderWidth(int type);
int getInvaderHeight(int type);
void addPointsByType(int type);
void killInvaderById(int id);
void refreshInvaders();

//Mystery.c
void initMystery();
void killMystery();

//Timers.c
void updateBullets();
void startTimers();
void stopTimers();
void refreshTimersFreq();

//Sounds.c
void soundsCallback();
void soundsStop();
void inverseMysteryFreq() ;

//variable definition
int game_stage = 0;
int wave = 0;

//==========================================
// 	MAIN.c
//==========================================


//-----------------------------------------------------------------
// main
//-----------------------------------------------------------------
int main(void) {

	//First initialization 
	//Welcome screens
	//In-game console
	init();
	//initFast();
	initGame();
	
	startTimers();
	initSounds();
	
	//-----------------------------------------------------------------
	// main loop
	//-----------------------------------------------------------------
	while(1)
	{
		if(game_stage==1) {
			//reacts for keys X, LEFT, RIGHT
			readKeys();
			
			swiWaitForVBlank();
			
			updateGraphics();
			
			checkForRefresh();
			checkForGameOver();
		} else if(game_stage==2) {
			gameOver();
			game_stage=3;
		} else {
			waitForRefreshGame();
		}

	}
	
	return 0;
}


//-----------------------------------------------------------------
// init
//-----------------------------------------------------------------
void init() {
	initConsole();
	initTitleScreen();
	initActionScreen();
	initGameScreen();
	initEndScreen();
	
	displayTitleScreen();
	while(active_screen < 1) {
        swiWaitForVBlank();
		if(title_screen.state==2) {
			waitForA();
		}
		if(active_screen==1)
			title_screen.state = 0;
    }
	clearConsole();
	displayActionScreen();
	while(active_screen < 2) {
        swiWaitForVBlank();
		if(action_screen.state==2) {
			waitForA();
		}
		if(active_screen==2)
			action_screen.state = 0;
    }
	endAnimation();
	clearConsole();
	active_screen=3;
	displayGameScreen();
	game_stage=1;
}

void initFast() {
	initConsole();
	initGameScreen();
	initEndScreen();
	active_screen=3;
	displayGameScreen();
	game_stage=1;
}

void initSounds() {
	soundEnable();
	
	/*
	
	int channel0 = 0;	//invaders moves
	int channel1 = 0;	//bullet sound
	int channel2a = 0;	//mystery noise
	int channel2b = 0;	//mystery sound
	int channel3 = 0;	//explosion
	*/
	//0-127
	
	channel0 = soundPlayPSG(DutyCycle_50, invaderFreq, 4, 64);
	channel1 = soundPlayPSG(DutyCycle_50, bulletFreq, 2, 64);
	channel2a = soundPlayNoise(mysteryNoiseFreq, 10, 64);
	channel2b = soundPlayPSG(DutyCycle_50, mysterySoundFreq, 10, 64);
	channel3a = soundPlayNoise(explosionNoiseFreq, 10, 64);
	channel3b = soundPlayPSG(DutyCycle_50, explosionFreq, 10, 64);
}

//-----------------------------------------------------------------
// initGame
//-----------------------------------------------------------------
void initGame() {
	initSpriteObjects();
	setupGraphics();
}




//-----------------------------------------------------------------
// readKeys
//-----------------------------------------------------------------
void readKeys() {
	int held;
	scanKeys();
	held = keysHeld();
	if( held & KEY_X) {
		player_fire();
	}
	
	if( held & KEY_LEFT) {
		player_moveL();
	}
	
	if( held & KEY_RIGHT) {
		player_moveR();
	}

}

//-----------------------------------------------------------------
// waitForA
//-----------------------------------------------------------------
void waitForA() {
	int held;
	scanKeys();
	held = keysHeld();
	if( held & KEY_A) {
		active_screen++;
	}
}

void waitForRefreshGame() {	//refresh when gameOver
	int held;
	scanKeys();
	held = keysHeld();
	if( held & KEY_A) {
		getHiScore();
		refreshGameScreenStatus();
		initPlayer();
		refreshMap();
		restoreLifes();
		clearConsole();
		displayGameScreen();
		game_stage=1;
		killedInvaders = 0;
		refreshEndScreenStatus();
	}
}

void checkForGameOver() {		//gameOver when any invader reach end point
	int iy, ih;
	int end = ship.y;
	
	for(int i=0; i<55; i++) {
		if(invaders[i].state==1) {
			iy=invaders[i].y;
			ih=getInvaderHeight(invaders[i].type);
			
			if(iy+ih >= end) {
				game_stage++;
				break;
			}
		}
	}
}


void gameOver() {
	stopTimers();
	soundsStop();
	clearConsole();
	finalScore();
	refreshEndScreenStatus();
	displayEndScreen();
	wave =0;
}

void goodGameOver() {
	stopTimers();
	soundsStop();
	clearConsole();
	finalScore();
	
	//displayEndScreen(); 		// <- zmiana na goodEndScreen
}


void checkForRefresh() {		
	if( (killedInvaders == 55)) {
		refreshMap();
	}
}

void refreshMap() {
	wave++;
	if(wave==9)
		goodGameOver();
		
	else {
		stopTimers();
		refreshTimersFreq();
		initMystery();
		refreshInvaders();
		soundsStop();
		startTimers();
	}
	
	
	
}


//==========================================
// 	GRAPHICS.c
//==========================================

//-----------------------------------------------------------------
// setupGraphics
//-----------------------------------------------------------------
void setupGraphics(void) {
	vramSetBankE( VRAM_E_MAIN_BG );
	vramSetBankF( VRAM_F_MAIN_SPRITE );
	
	dmaCopyHalfWords( 3, gfx_shipTiles, tile2objram(tile4(tiles_ship)), gfx_shipTilesLen );
	dmaCopyHalfWords( 3, gfx_invader1Tiles, tile2objram(tile4(tiles_invader1)), gfx_invader1TilesLen );
	dmaCopyHalfWords( 3, gfx_invader2Tiles, tile2objram(tile4(tiles_invader2)), gfx_invader2TilesLen );
	dmaCopyHalfWords( 3, gfx_invader3Tiles, tile2objram(tile4(tiles_invader3)), gfx_invader3TilesLen );
	dmaCopyHalfWords( 3, gfx_fire1Tiles, tile2objram(tile4(tiles_fire1)), gfx_fire1TilesLen );
	dmaCopyHalfWords( 3, gfx_fire2Tiles, tile2objram(tile4(tiles_fire2)), gfx_fire2TilesLen );
	dmaCopyHalfWords( 3, gfx_fire3Tiles, tile2objram(tile4(tiles_fire3)), gfx_fire3TilesLen );
	dmaCopyHalfWords( 3, gfx_mysteryTiles, tile2objram(tile4(tiles_mystery)), gfx_mysteryTilesLen );
	
	dmaCopyHalfWords( 3, gfx_shipPal, pal2objram(pal_ship), gfx_shipPalLen );
	dmaCopyHalfWords( 3, gfx_invader1Pal, pal2objram(pal_invader1), gfx_invader1PalLen );
	dmaCopyHalfWords( 3, gfx_invader2Pal, pal2objram(pal_invader2), gfx_invader2PalLen );
	dmaCopyHalfWords( 3, gfx_invader3Pal, pal2objram(pal_invader3), gfx_invader3PalLen );
	dmaCopyHalfWords( 3, gfx_fire1Pal, pal2objram(pal_fire1), gfx_fire1PalLen );
	dmaCopyHalfWords( 3, gfx_fire2Pal, pal2objram(pal_fire2), gfx_fire2PalLen );
	dmaCopyHalfWords( 3, gfx_fire3Pal, pal2objram(pal_fire3), gfx_fire3PalLen );
	dmaCopyHalfWords( 3, gfx_mysteryPal, pal2objram(pal_mystery), gfx_mysteryPalLen );
	

	//asign color to background
	BG_PALETTE[0] = backdrop_colour;
	videoSetMode( MODE_0_2D | DISPLAY_BG0_ACTIVE | DISPLAY_BG1_ACTIVE | DISPLAY_SPR_ACTIVE | DISPLAY_SPR_1D_LAYOUT );
	
	for(int n = 0; n < 128; n++ ) {
		sprites[n].attr0 = ATTR0_DISABLED;
	}


	//ship (id 0)
	sprites[0].attr0 = ship.y;
	sprites[0].attr1 = ship.x + ATTR1_SIZE_16;
	sprites[0].attr2 = tile4(tiles_ship) + (pal_ship << 12);
	

	//invaders (id 1-55)
	for(int n = 0; n <55; n++) {
		int id = invaders[n].id;
		sprites[id].attr0 = invaders[n].y;
		sprites[id].attr1 = invaders[n].x + ATTR1_SIZE_16;
		
		if(invaders[n].type == 0)
			sprites[id].attr2 = tile4(tiles_invader1) + (pal_invader1 << 12);
		if(invaders[n].type == 1)
			sprites[id].attr2 = tile4(tiles_invader2) + (pal_invader2 << 12);
		if(invaders[n].type == 2)
			sprites[id].attr2 = tile4(tiles_invader3) + (pal_invader3 << 12);
		
	}
	
	//lifes (56-58)
	for(int i=0; i<ship.lifes; i++) {
		sprites[56+i].attr0 = BOARD_HEIGHT - 16;
		sprites[56+i].attr1 = 1+i*17 + ATTR1_SIZE_16;
		sprites[56+i].attr2 = tile4(tiles_ship) + (pal_ship << 12);
	}
	
	
	//bullets
		//sprites[59].attr0 = BOARD_HEIGHT;
		//sprites[59].attr1 = BOARD_WIDTH + ATTR1_SIZE_16;
	for(int i=0; i<4; i++) {
		sprites[59+i].attr0 = -20;
		sprites[59+i].attr1 = 0;
		sprites[59+i].attr2 = tile4(tiles_fire1) + (pal_fire1 << 12);
	}
	
	//mystery ship
	sprites[63].attr0 = 2;
	sprites[63].attr1 = BOARD_WIDTH + ATTR1_SIZE_16;
	sprites[63].attr2 = tile4(tiles_mystery) + (pal_mystery << 12);
	
}

//-----------------------------------------------------------------
// updateGraphics
//-----------------------------------------------------------------
void updateGraphics(void) {
	//player
	sprites[0].attr0 = ship.y;
	sprites[0].attr1 = ship.x + ATTR1_SIZE_16;
	
	//invaders (id 1-55)
	for(int n = 0; n <55; n++) {
		int id = invaders[n].id;
		if(invaders[n].state==1) {
			sprites[id].attr0 = invaders[n].y;
			sprites[id].attr1 = invaders[n].x + ATTR1_SIZE_16;
		} else {
			sprites[id].attr0 = -16;
			sprites[id].attr1 = 0;
		}
	}
	
	
	//player bullet
	sprites[59].attr0 = ship.bullet->y;
	sprites[59].attr1 = ship.bullet->x + ATTR1_SIZE_16;
	
	//invader bullets
	for(int i=1; i<=MAX_I_SHOOTS; i++) {
		sprites[59+i].attr0 = bullets[i].y;
		sprites[59+i].attr1 = bullets[i].x + ATTR1_SIZE_16;
	}
	
	//mystery
	if(mystery.state==1) {
		sprites[mystery.id].attr0 = mystery.y;
		sprites[mystery.id].attr1 = mystery.x  + ATTR1_SIZE_16;
	} else {
		sprites[mystery.id].attr1 = BOARD_WIDTH + ATTR1_SIZE_16;
	}
	

}

void refreshLifes() {
	sprites[56+ship.lifes].attr1 = -16;
}

void restoreLifes() {
		//lifes (56-58)
	for(int i=0; i<ship.lifes; i++) {
		sprites[56+i].attr0 = BOARD_HEIGHT - 16;
		sprites[56+i].attr1 = 1+i*17 + ATTR1_SIZE_16;
	}
}

//=========================================
// Sprites.c
//=========================================

void initSpriteObjects() {
	initPlayer();
	initMystery();
}

//=========================================
// Bullet.c
//=========================================


int getBulletWidth(int type) {
	switch(type) {
		case 0:
			return 2;
			break;
		case 1:
			return 3;
			break;
		case 2:
			return 3;
			break;
		default:
			return 0;
	}
}

int getBulletHeight(int type) {
	switch(type) {
		case 0:
			return 8;
			break;
		case 1:
			return 8;
			break;
		case 2:
			return 8;
			break;
		default:
			return 0;
	}
}

void removeBullets() {
	for(int i=1; i<4; i++) {
		bullets[i].state=0;
	}
	invadersBullets = 0;

}

void killBulletById(int id) {
	bullets[id].state=0;
	invadersBullets--;
}


//===========================================
// Player.c
//===========================================

void player_moveL() {
	if(ship.x>0) 
		ship.x--;
}

void player_moveR() {
	if(ship.x< (BOARD_WIDTH-ship.width))
		ship.x++;
}

void player_fire(){
	//any player's bullet
	if(ship.bullet->state==0) {
		//create new bullet (initialize their states)
		ship.bullet->x=ship.x+6;
		ship.bullet->y=ship.y+8;
		ship.bullet->state=1;
	}
}

void initPlayer(){
	ship.lifes = 3;
	ship.x = BOARD_WIDTH/2 - 8;
	ship.y = BOARD_HEIGHT-P_Y_POSITION;
	ship.width= 16;
	ship.height= 8;
	ship.moving = 0;
	ship.id=0;
	ship.bullet=&bullets[0];
	
	ship.bullet->type=1;
	ship.bullet->dy=1;
}

void updatePlayerBullet() {
	int invaderId=1;
	int mysteryCol = 0;
	int bulletId = -1;
	//player bullet
	if(bullets[0].state==1) {
		//bullet sound
		bulletSounds=true;
		
		if(bullets[0].y>0) {
			bullets[0].y-=2;
			//detect collisions
			invaderId = checkCollisionWithInvaders(bullets[0].x, bullets[0].y);
			if(invaderId!= -1) {
				killInvaderById(invaderId);
				bullets[0].state=0;
				bulletSounds=false;
			}
			
			//detectCollision with mystery
			mysteryCol = checkCollisionWithMystery(bullets[0].x, bullets[0].y);
			if(mysteryCol == 1) {
				killMystery();
				bullets[0].state=0;
				bulletSounds=false;
			}
			
			//detectCollision with invadersBullets
			bulletId = checkCollisionWithBullets(bullets[0].x, bullets[0].y);
			if(bulletId!=-1) {
				killBulletById(bulletId);
				bullets[0].state=0;
				bulletSounds=false;
			}
			
		} else {
			//hit border
			bullets[0].state=0;
			bulletSounds=false;
		
		}
	} else {
		bullets[0].y=300;
		bulletSounds=false;
	}
}

int checkCollisionWithPlayer(int x,int y) {

	int bw, bh, ix, iy,px, py, pw, ph;
	pw = ship.width;
	ph = ship.height;
	
	for(int i = 1; i<3; i++) {
		//check if bullet still exists
		if(bullets[i].state==1) {
			ix = bullets[i].x;
			iy = bullets[i].y;
			
			bw = getBulletWidth(bullets[i].type);
			bh = getBulletHeight(bullets[i].type);
			
			px = ship.x;
			py = ship.y;
			
			if(( (ix >= px) && (ix <= px+pw) ) && (iy >= py+3) && (iy <= py + ph))
				return i;
			
			if(( (ix >= px) && (ix <= px+pw) ) && (iy+bh >= py+3) && (iy + bh <= py + ph))
				return i;
				
			if(( (ix + bw >= px) && (ix + bw <= px+pw) ) && (iy >= py+3) && (iy <= py + ph))
				return i;
				
			if(( (ix + bw >= px) && (ix + bw<= px+pw) ) && (iy+bh >= py+3) && (iy + bh <= py + ph))
				return i;
		}
	}
	return -1;
}



int checkCollisionWithBullets(int bx,int by) {

	int bw, bh, bw2, bh2, bx2, by2;
	
	bw = getBulletWidth(bullets[0].type);
	bh = getBulletHeight(bullets[0].type);
	
	for(int i = 1; i<3; i++) {
		//check if bullet still exists
		if(bullets[i].state==1) {
			bx2 = bullets[i].x;
			by2 = bullets[i].y;
			
			bw2 = getBulletWidth(bullets[i].type);
			bh2 = getBulletHeight(bullets[i].type);
			
			if(( (bx >= bx2) && (bx <= bx2+bw2) ) && (by >= by2) && (by <= by2 + bh2))
				return i;
			
			if(( (bx >= bx2) && (bx <= bx2+bw2) ) && (by+bh >= by2) && (by + bh <= by2 + bh2))
				return i;
				
			if(( (bx + bw >= bx2) && (bx + bw <= bx2+bw2) ) && (by >= by2) && (by <= by2 + bh2))
				return i;
				
			if(( (bx + bw >= bx2) && (bx + bw<= bx2+bw2) ) && (by+bh >= by2) && (by + bh <= by2 + bh2))
				return i;
		}
	}
	return -1;
}





void killPlayer() {
	ship.lifes--;
	explosionSounds=true;
	if(ship.lifes==0)
		//gameOver();
		game_stage++;
	removeBullets();
	centerPlayer();
	refreshLifes();
}

void centerPlayer() {
	ship.x =  BOARD_WIDTH/2 - 8;
}

//=============================================
// Mystery.c
//=============================================

void initMystery() {
	mystery.id=63;
	mystery.type=3;
	mystery.x = BOARD_WIDTH + ATTR1_SIZE_16;
	mystery.y = 2;
	mystery.state = 0;
	mystery.value = 0;
}

void updateMystery() {
	if(mystery.state==0) {
		mysterySounds=false;
		int r = rand() % 10000;
		if(r<10) {
			mystery.x=BOARD_WIDTH;
			int val = (rand() % 6) * 50 + 100;
			mystery.value = val;
			mystery.state=1;
		}
	} else {
		mysterySounds=true;
		//moving
		if(mystery.x>=-16) {
			mystery.x-=3;
		} else {
			mystery.state=0;
		}
	}
}

void killMystery() {
	mystery.state=0;
	addPointsByType(mystery.type);
	mystery.x = -20;
}

//=============================================
// Invader.c
//=============================================

void updateInvaders() {	
	invadersSounds=true;
	
	if(invadersDirection==1) {
		if(invaderMoves<MAX_HOR_MOVES) {    //moving right
			for(int i=0; i<55; i++) {
				if(invaders[i].state==1)
					invaders[i].x++;
			}
			invaderMoves++;
		} else {					    //moving down
			for(int i=0; i<55; i++) {
				if(invaders[i].state==1)
					invaders[i].y+=3;
			}
			invaderMoves=0;
			invadersDirection=-1;
		}
		
	} else if(invadersDirection == -1) {
	
		if(invaderMoves<MAX_HOR_MOVES) {    //moving left
			for(int i=0; i<55; i++) {
				if(invaders[i].state==1)
					invaders[i].x--;
			}
			invaderMoves++;
		} else {					    //moving down
			for(int i=0; i<55; i++) {
				if(invaders[i].state==1)
					invaders[i].y+=3;
			}
			invaderMoves=0;
			invadersDirection=1;
		}
		
	}

	int bulletChance = rand()%100;
	if((invadersBullets < MAX_I_SHOOTS) && (bulletChance < BULLET_I_CHANCE)) {
		invadersBullets++;
		int freeBullet = 0;
		for(int i=1; i<4; i++){
			if(bullets[i].state==0) {
				freeBullet=i;
				break;
			}
		}
		
		//find living free invader
		int k = 0;
		int invaderID =0;
		while(k==0) {
			invaderID = rand()%55;
			if(invaders[invaderID].state==1)
				k++;
		}
		
		
		bullets[freeBullet].state = 1;
		bullets[freeBullet].y = invaders[invaderID].y+4;
		bullets[freeBullet].x = invaders[invaderID].x+4;
	}
	
	
	updateMystery();
	
	
}

void updateInvaderBullets() {
	int id = -1;
	//invader bullets
	for(int i=1; i<4; i++) {
		if(bullets[i].state==1) {
			if(bullets[i].y<ship.y+8) {
				bullets[i].y++;
				id = checkCollisionWithPlayer(bullets[i].x, bullets[i].y);
				if(id!=-1) {
					killPlayer();
					bullets[i].state=0;
				}
				
			} else {
				//hit border
				bullets[i].state=0;
				invadersBullets--;
			}
		} else {
			bullets[i].y=300;
		}
	}
}

int checkCollisionWithInvaders(int x, int y) {
	int iw,ih, bw, bh, ir, br, ix, ixx, iy, iyy, bx, by, px1, px2, py1, py2;
	
	for(int i = 0; i<55; i++) {
		//check if invader still exists
		if(invaders[i].state==1) {
			ixx = invaders[i].x;
			iyy = invaders[i].y;
			
			//frist Cirle collision detection 
			//for little cost cutting
			//i know that bullet sprite is 8x8 sized but the 
			//real image representation is 2x8 size and starts from left
			//it implicites that the center of that bullet is in (1,3) and
			//the radius is equal 4px
			//each invader has size
			iw=getInvaderWidth(invaders[i].type);
			ih=getInvaderHeight(invaders[i].type);
			bw=getBulletWidth(bullets[0].type);
			bh=getBulletHeight(bullets[0].type);
			
			//calculate center points
			ix = invaders[i].x + iw/2;
			iy = invaders[i].y + ih/2;
			bx = x+bw/2;
			by = y+bh/2;
			
			//calculate radius
			br = (int) sqrt( (bw*bw/4+bh*bh/4));
			ir = (int) sqrt( (iw*iw/4+ih*ih/4));
			//compare distance with radiuses
			if( sqrt (((ix-bx)*(ix-bx)) + ((iy-by)*(iy-by))) < (br+ir)) {
				//more detailed collision detect - rectangle collision (only borders)
				//the best one would be pixel collision detection with color comparing
				for(py1=0;py1<bh;py1++) {
					//bottom, top
					if((py1==0) || (py1==bh)) {
						for(px1=0;px1<bw;px1++) {
							
							//compare with border in invader
							for(py2=0; py2<ih; py2++) {
								//bottom,top
								if((py2 == 0) || (py2==ih)) {
									for(px2=0; px2<iw; px2++) {
										//compare coliision
										
										if ((x + px1 == ixx + px2) && 
											(y + py1 == iyy + py2))
												//collision
												return i;
										
									}
								} else {
									
									//invader left, right collision
									
									if ((x + px1 == ixx) && 
										(y + py1 == iyy + py2))
												//collision
												return i;
												
									if ((x + px1 == ixx + iw) && 
										(y + py1 == iyy + py2))
												//collision
												return i;
								
								
								}
								
							}
								
						}
					} else {
					
						//left, right
						//compare collision with all borders in invader
						for(py2=0; py2<ih; py2++) {
							//bottom,top
							if((py2 == 0) || (py2==ih)) {
								for(px2=0; px2<iw; px2++) {
									//compare coliision for left
										if ((x == ixx + px2) && 
											(y + py1 == iyy + py2))
												//collision
												return i;			

									//compare coliision for right
										if ((x + bw == ixx + px2) && 
											(y + py1 == iyy + py2))
												//collision
												return i;								
								}
							} else {
							
								//compare coliision for left
								if ((x == ixx) && 
									(y + py1 == iyy + py2))
										//collision
										return i;			

								//compare coliision for left
								if ((x + bw== ixx) && 
									(y + py1 == iyy + py2))
										//collision
										return i;		

								//compare coliision for right
								if ((x == ixx + px2) && 
									(y + py1 == iyy + py2))											//collision
										return i;		

								
								//compare coliision for right
								if ((x + bw == ixx + px2) && 
									(y + py1 == iyy + py2))											//collision
										return i;	
								
							}
							
						}
					
					}
					
				}
				
			}
 		}
	}
	return -1;
}


int checkCollisionWithMystery(int bx, int by) {
	int mx, my, mw, mh, bw, bh;
	if(mystery.state==1) {
		mx = mystery.x;
		my = mystery.y;
		mw = getInvaderWidth(mystery.type);
		mh = getInvaderHeight(mystery.type);
	
		bw = getBulletWidth(bullets[0].type);
		bh = getBulletHeight(bullets[0].type);
		
			if(( (bx >= mx) && (bx <= mx+mw) ) && (by >= my) && (by <= my + mh))
				return 1;
			
			if(( (bx >= mx) && (bx <= mx+mw) ) && (by+bh >= my) && (by + bh <= my + mh))
				return 1;
				
			if(( (bx + bw >= mx) && (bx + bw <= mx+mw) ) && (by >= my) && (by <= my + mh))
				return 1;
				
			if(( (bx + bw >= mx) && (bx + bw<= mx+mw) ) && (by+bh >= my) && (by + bh <= my + mh))
				return 1;
	} 
	return 0;
} 


int getInvaderWidth(int type) {
	switch(type) {
		case 0:
			return 8;
			break;
		case 1:
			return 12;
			break;
		case 2:
			return 16;
			break;
		case 3:
			return 16;
			break;
		default:
			return 0;
	}
}

int getInvaderHeight(int type) {
	switch(type) {
		case 0:
			return 8;
			break;
		case 1:
			return 10;
			break;
		case 2:
			return 10;
			break;
		case 3:
			return 9;
			break;
		default:
			return 0;
	}
}


void addPointsByType(int type) {
	switch(type) {
		case 0:
			addScore(10);
			break;
		case 1:
			addScore(20);
			break;
		case 2:
			addScore(30);
			break;
		case 3:
			addScore(mystery.value); //range 100 - 400 with interval 50;
			break;
			
	}
}

void killInvaderById(int id) {
	explosionSounds=!explosionSounds;
	invaders[id].state=0;
	addPointsByType(invaders[id].type);
	killedInvaders++;
	if(killedInvaders%10 == 0) {
		timerStop(invaderTimer_id);
		invaderTimer_vel+=5;
		timerStart(invaderTimer_id, ClockDivider_1024, TIMER_FREQ_1024(invaderTimer_vel), updateInvaders);

	}
}


void refreshInvaders() {
	int id = 0;
	for(int k=0; k<5; k++) {
		for(int i=0; i < 11; i++) {
			invaders[id].x = 7 + (19*i);
			invaders[id].y = 15 + (k*19);
			invaders[id].state = 1;
			id++;
		}
	}
	invaderMoves = 0;
	invadersDirection = 1;
	invadersBullets = 0;
	killedInvaders = 0;
}

//===============================================
// Timers.c
//===============================================


void updateBullets() {
	updatePlayerBullet();
	updateInvaderBullets();
}


void startTimers() {
	timerStart(invaderTimer_id, ClockDivider_1024, TIMER_FREQ_1024(invaderTimer_vel), updateInvaders);
	timerStart(bulletTimer_id, ClockDivider_1024, TIMER_FREQ_1024(bulletTimer_vel), updateBullets);
	timerStart(soundsTimer_id, ClockDivider_1024, TIMER_FREQ_1024(soundsTimer_vel), soundsCallback);
}

void refreshTimersFreq() {
	invaderTimer_vel = 3;  
	bulletTimer_vel = 80;
	soundsTimer_vel = 30;
}

void stopTimers() {
	timerStop(invaderTimer_id);
	timerStop(bulletTimer_id);
	timerStop(soundsTimer_id);
}

//===============================================
// Sounds.c
//===============================================

void soundsStop() {
	invadersSounds = false;
	bulletSounds = false;
	mysterySounds = false;
	explosionSounds = false;
	
	soundPause(channel0);
	soundPause(channel1);
	soundPause(channel2a);
	soundPause(channel2b);
	soundPause(channel3a);
	soundPause(channel3b);
}

void soundsCallback() {
/*
bool invadersSounds = true;
bool bulletSounds = true;
bool mysterySounds = true;
bool explosionSounds = true;
*/
	/*
	
	int channel0 = 0;	//invaders moves
	int channel1 = 0;	//bullet sound
	int channel2a = 0;	//mystery noise
	int channel2b = 0;	//mystery sound
	int channel3 = 0;	//explosion
	*/


	if(!invadersSounds) {
		soundPause(channel0);
	} else {
		soundResume(channel0);
		invadersSounds=!invadersSounds;	
	}
	
	
	if(!bulletSounds) {
		soundPause(channel1);
	} else {
		soundResume(channel1);	
	}
	
	if(!mysterySounds) {
		soundPause(channel2a);
		soundPause(channel2b);
		inverseMysteryFreq();
	} else {
		soundResume(channel2a);	
		soundResume(channel2b);	
	}
	
	if(explosionSounds) {
		soundPause(channel3a);
		soundPause(channel3b);
	} else {
		soundResume(channel3a);
		soundResume(channel3b);
		explosionSounds=!explosionSounds;	
	}
	
}

void inverseMysteryFreq() {
	if(mysteryNoiseFreq==2500) {
		mysteryNoiseFreq = 100;
		mysterySoundFreq = 2500;
	} else {
		mysteryNoiseFreq = 2500;
		mysterySoundFreq = 400;
	}
	
	soundSetFreq(channel2a, mysteryNoiseFreq);
	soundSetFreq(channel2b, mysterySoundFreq);
} 
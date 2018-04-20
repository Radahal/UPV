#include <nds.h>
#include <screens.h>
#include <string.h>
#include <stdio.h>

//-----------------------------------------------------------------
// displayTitleScreen
//-----------------------------------------------------------------
void displayTitleScreen() { 
	active_screen = 0;
	timer_id = 0;
	timerStart(timer_id, ClockDivider_1024, TIMER_FREQ_1024(title_screen.msg_play.velocity), animateTitleScreen);
}

//-----------------------------------------------------------------
// changeTitleScreenVelocity 
//-----------------------------------------------------------------
void changeTitleScreenVelocity(u8 v) {
	endAnimation();
	timerStart(timer_id, ClockDivider_1024, TIMER_FREQ_1024(v), animateTitleScreen);
}

//-----------------------------------------------------------------
// displayActionScreen
//-----------------------------------------------------------------
void displayActionScreen() {
	active_screen = 1;
	timer_id = 0;
	timerStart(timer_id, ClockDivider_1024, TIMER_FREQ_1024(action_screen.msg_push.velocity), animateActionScreen);
}

//-----------------------------------------------------------------
// changeActionScreenVelocity 
//-----------------------------------------------------------------
void changeActionScreenVelocity(u8 v) {
	endAnimation();
	timerStart(timer_id, ClockDivider_1024, TIMER_FREQ_1024(v), animateActionScreen);
}

//-----------------------------------------------------------------
// displayGameScreen
//-----------------------------------------------------------------
void displayGameScreen() {
	active_screen = 3;
	timer_id = 0;
	timerStart(timer_id, ClockDivider_1024, TIMER_FREQ_1024(game_screen.velocity), refreshGameScreen);
}

//-----------------------------------------------------------------
// displayEndScreen 
//-----------------------------------------------------------------

void displayEndScreen() { 
	active_screen = 4;
	timer_id = 0;
	timerStart(timer_id, ClockDivider_1024, TIMER_FREQ_1024(end_screen.msg_end.velocity), animateEndScreen);
}

//-----------------------------------------------------------------
// changeEndScreenVelocity 
//-----------------------------------------------------------------
void changeEndScreenVelocity(u8 v) {
	endAnimation();
	timerStart(timer_id, ClockDivider_1024, TIMER_FREQ_1024(v), animateEndScreen);
}

//-----------------------------------------------------------------
// animateTitleScreen
//-----------------------------------------------------------------
void animateTitleScreen() {
	
		switch(title_screen.msg) {
			case 0:
				if(title_screen.msg_play.state) {
					printMessage(&title_screen.msg_play);
				} else {
					title_screen.msg++;
					if(title_screen.msg_play.velocity != title_screen.msg_title.velocity)
						changeTitleScreenVelocity(title_screen.msg_title.velocity);
				}
				break;
			case 1:
				if(title_screen.msg_title.state) {
					printMessage(&title_screen.msg_title);
				} else {
					title_screen.msg++;
					if(title_screen.msg_title.velocity != title_screen.msg_scoreHeader.velocity)
						changeTitleScreenVelocity(title_screen.msg_scoreHeader.velocity);
				}
				break;
			case 2:
				if(title_screen.msg_scoreHeader.state) {
					printMessage(&title_screen.msg_scoreHeader);
				} else {
					title_screen.msg++;
					if(title_screen.msg_scoreHeader.velocity != title_screen.msg_scoreMystery.velocity)
						changeTitleScreenVelocity(title_screen.msg_scoreMystery.velocity);
					setColor(RED);
				}
				break;
			case 3:
				if(title_screen.msg_scoreMystery.state) {
					printMessage(&title_screen.msg_scoreMystery);
				} else {
					title_screen.msg++;
					if(title_screen.msg_scoreMystery.velocity != title_screen.msg_score30.velocity)
						changeTitleScreenVelocity(title_screen.msg_score30.velocity);
					setColor(WHITE);
				}
				break;
			case 4:
				if(title_screen.msg_score30.state) {
					printMessage(&title_screen.msg_score30);
				} else {
					title_screen.msg++;
					if(title_screen.msg_score30.velocity != title_screen.msg_score20.velocity)
						changeTitleScreenVelocity(title_screen.msg_score20.velocity);
				}
				break;
			case 5:
				if(title_screen.msg_score20.state) {
					printMessage(&title_screen.msg_score20);
				} else {
					title_screen.msg++;
					if(title_screen.msg_score20.velocity != title_screen.msg_score10.velocity)
						changeTitleScreenVelocity(title_screen.msg_score10.velocity);
					setColor(GREEN);
				}
				break;
			case 6:
				if(title_screen.msg_score10.state) {
					printMessage(&title_screen.msg_score10);
				} else {
					title_screen.msg++;
					if(title_screen.msg_score10.velocity != title_screen.msg_next.velocity)
						changeTitleScreenVelocity(title_screen.msg_next.velocity);
					setColor(WHITE);
					title_screen.state=2;
				}
				break;
			case 7:
				printWholeMessage(&title_screen.msg_next);
				title_screen.msg++;
				break;
			case 8:
				clearLine(title_screen.msg_next.cursor_line);
				title_screen.msg_next.state=1;
				title_screen.msg--;
				break;
			default:
				title_screen.state=0;
				endAnimation();
			
		}
}


//-----------------------------------------------------------------
// animateActionScreen
//-----------------------------------------------------------------
void animateActionScreen() {
	
		switch(action_screen.msg) {
			case 0:
				if(action_screen.msg_push.state) {
					printMessage(&action_screen.msg_push);
				} else {
					action_screen.msg++;
					if(action_screen.msg_push.velocity != action_screen.msg_arrows.velocity)
						changeActionScreenVelocity(action_screen.msg_arrows.velocity);
				}
				break;
			case 1:
				if(action_screen.msg_arrows.state) {
					printMessage(&action_screen.msg_arrows);
				} else {
					action_screen.msg++;
					if(action_screen.msg_arrows.velocity != action_screen.msg_buttonX.velocity)
						changeActionScreenVelocity(action_screen.msg_buttonX.velocity);
				}
				break;
			case 2:
				if(action_screen.msg_buttonX.state) {
					printMessage(&action_screen.msg_buttonX);
				} else {
					action_screen.msg++;
					if(action_screen.msg_buttonX.velocity != action_screen.msg_next.velocity)
						changeActionScreenVelocity(action_screen.msg_next.velocity);
					action_screen.state=2;
				}
				break;
			case 3:
				printWholeMessage(&action_screen.msg_next);
				action_screen.msg++;
				break;
			case 4:
				clearLine(action_screen.msg_next.cursor_line);
				action_screen.msg_next.state=1;
				action_screen.msg--;
				break;
			default:
				action_screen.state=0;
				endAnimation();
			
		}
}

//-----------------------------------------------------------------
// animateEndScreen
//-----------------------------------------------------------------
void animateEndScreen() {
	
		switch(end_screen.msg) {
			case 0:
				if(end_screen.msg_end.state) {
					printMessage(&end_screen.msg_end);
				} else {
					end_screen.msg++;
					if(end_screen.msg_end.velocity != end_screen.msg_scoreLabel.velocity)
						changeEndScreenVelocity(end_screen.msg_scoreLabel.velocity);
				}
				break;
			case 1:
				if(end_screen.msg_scoreLabel.state) {
					printMessage(&end_screen.msg_scoreLabel);
				} else {
					end_screen.msg++;
					if(end_screen.msg_scoreLabel.velocity != end_screen.msg_score.velocity)
						changeEndScreenVelocity(end_screen.msg_score.velocity);
				}
				break;
			case 2:
				if(end_screen.msg_score.state) {
					printScoreMessage(&end_screen.msg_score, &end_screen.score);
				} else {
					end_screen.msg++;
					if(end_screen.msg_score.velocity != end_screen.msg_newGame.velocity)
						changeEndScreenVelocity(end_screen.msg_newGame.velocity);
					end_screen.state=2;
				}
				break;
			case 3:
				printWholeMessage(&end_screen.msg_newGame);
				end_screen.msg++;
				break;
			case 4:
				clearLine(end_screen.msg_newGame.cursor_line);
				end_screen.msg_newGame.state=1;
				end_screen.msg--;
				break;
			default:
				end_screen.state=0;
				endAnimation();
			
		}
		
		
		
		
		
}


//-----------------------------------------------------------------
// refreshGameScreen
//-----------------------------------------------------------------
void refreshGameScreen() {
	//setText(&game_screen.msg_score, score);
	game_screen.msg_score.state=1;
	game_screen.msg_hiScore.state=1;
	printWholeMessage(&game_screen.msg_scoreLabel);
	printWholeMessage(&game_screen.msg_hiScoreLabel);
	printScoreMessage(&game_screen.msg_score, &game_screen.score);
	printScoreMessage(&game_screen.msg_hiScore, &game_screen.hiScore);
}





//-----------------------------------------------------------------
// endAnimation
//-----------------------------------------------------------------
void endAnimation() {
	timerStop(timer_id);
}


//-----------------------------------------------------------------
// initTitleScreen
//-----------------------------------------------------------------
void initTitleScreen() {
	struct CMessage msg_play, msg_title, msg_scoreHeader, msg_scoreMystery, msg_score30, msg_score20, msg_score10, msg_next;
	struct Screen1 screen1;
	
	msg_play.state = 1;
	strcpy(msg_play.text,"PLAY");
	msg_play.current_char= 0;
	msg_play.velocity = 8;
	msg_play.cursor_col0 = 14;
	msg_play.cursor_line0 = 4;
	msg_play.cursor_col = 14;
	msg_play.cursor_line = 4;
	
	msg_title.state = 1;
	strcpy(msg_title.text,"SPACE INVADERS");
	msg_title.current_char = 0;
	msg_title.velocity = 8;						
	msg_title.cursor_col0 = 9;
	msg_title.cursor_line0 = 7;
	msg_title.cursor_col = 9;
	msg_title.cursor_line = 7;
	
	msg_scoreHeader.state = 1;
	strcpy(msg_scoreHeader.text,"*SCORE ADVANCE TABLE*");
	msg_scoreHeader.current_char = 0;
	msg_scoreHeader.velocity = 8;						
	msg_scoreHeader.cursor_col0 = 6;
	msg_scoreHeader.cursor_line0 = 10;
	msg_scoreHeader.cursor_col = 6;
	msg_scoreHeader.cursor_line = 10;

	msg_scoreMystery.state = 1;
	strcpy(msg_scoreMystery.text,"=? MYSTERY");
	msg_scoreMystery.current_char = 0;
	msg_scoreMystery.velocity = 8;						
	msg_scoreMystery.cursor_col0 = 12;
	msg_scoreMystery.cursor_line0 = 12;
	msg_scoreMystery.cursor_col = 12;
	msg_scoreMystery.cursor_line = 12;
	
	
	msg_score30.state = 1;
	strcpy(msg_score30.text,"=30 POINTS");
	msg_score30.current_char = 0;
	msg_score30.velocity = 8;						
	msg_score30.cursor_col0 = 12;
	msg_score30.cursor_line0 = 14;
	msg_score30.cursor_col = 12;
	msg_score30.cursor_line = 14;
	
	
	msg_score20.state = 1;
	strcpy(msg_score20.text,"=20 POINTS");
	msg_score20.current_char = 0;
	msg_score20.velocity = 8;						
	msg_score20.cursor_col0 = 12;
	msg_score20.cursor_line0 = 16;
	msg_score20.cursor_col = 12;
	msg_score20.cursor_line = 16;
	
	
	msg_score10.state = 1;
	strcpy(msg_score10.text,"=10 POINTS");
	msg_score10.current_char = 0;
	msg_score10.velocity = 8;						
	msg_score10.cursor_col0 = 12;
	msg_score10.cursor_line0 = 18;
	msg_score10.cursor_col = 12;
	msg_score10.cursor_line = 18;
	
	msg_next.state = 1;
	strcpy(msg_next.text,"PUSH A TO CONTINUE");
	msg_next.current_char = 0;
	msg_next.velocity = 2;						
	msg_next.cursor_col0 = 8;
	msg_next.cursor_line0 = 21;
	msg_next.cursor_col = 8;
	msg_next.cursor_line = 21;

	screen1.msg_play = msg_play;
	screen1.msg_title = msg_title;
	screen1.msg_scoreHeader = msg_scoreHeader;
	screen1.msg_scoreMystery = msg_scoreMystery;
	screen1.msg_score30 = msg_score30;
	screen1.msg_score20 = msg_score20;
	screen1.msg_score10 = msg_score10;
	screen1.msg_next = msg_next;
	screen1.state = 1;
	
	
	screen1.msg = 0;
	title_screen = screen1;
}

//-----------------------------------------------------------------
// initActionScreen
//-----------------------------------------------------------------
void initActionScreen() {
	struct CMessage msg_push, msg_arrows, msg_buttonX, msg_next;
	struct Screen2 screen2;
	
	msg_push.state = 1;
	strcpy(msg_push.text,"PUSH");
	msg_push.current_char= 0;
	msg_push.velocity = 8;
	msg_push.cursor_col0 = 14;
	msg_push.cursor_line0 = 4;
	msg_push.cursor_col = 14;
	msg_push.cursor_line = 4;

	
	msg_arrows.state = 1;
	strcpy(msg_arrows.text,"ARROWS TO MOVE");
	msg_arrows.current_char = 0;
	msg_arrows.velocity = 8;						
	msg_arrows.cursor_col0 = 6;
	msg_arrows.cursor_line0 = 8;
	msg_arrows.cursor_col = 6;
	msg_arrows.cursor_line = 8;
	
	msg_buttonX.state = 1;
	strcpy(msg_buttonX.text,"BUTTON 'X' TO FIRE");
	msg_buttonX.current_char = 0;
	msg_buttonX.velocity = 8;						
	msg_buttonX.cursor_col0 = 6;
	msg_buttonX.cursor_line0 = 11;
	msg_buttonX.cursor_col = 6;
	msg_buttonX.cursor_line = 11;

	
	msg_next.state = 1;
	strcpy(msg_next.text,"BUTTON 'A' TO START");
	msg_next.current_char = 0;
	msg_next.velocity = 2;						
	msg_next.cursor_col0 = 6;
	msg_next.cursor_line0 = 14;
	msg_next.cursor_col = 6;
	msg_next.cursor_line = 14;


	screen2.msg_push = msg_push;
	screen2.msg_arrows = msg_arrows;
	screen2.msg_buttonX = msg_buttonX;
	screen2.msg_next = msg_next;
	screen2.state = 1;	
	screen2.msg = 0;
	
	action_screen = screen2;
}


//-----------------------------------------------------------------
// initGameScreen
//-----------------------------------------------------------------

void initGameScreen() {
	struct CMessage msg_scoreLabel, msg_hiScoreLabel, msg_score, msg_hiScore;
	struct Screen3 screen3;
	
	msg_scoreLabel.state = 1;
	strcpy(msg_scoreLabel.text,"SCORE");
	msg_scoreLabel.current_char= 0;
	msg_scoreLabel.velocity = 8;
	msg_scoreLabel.cursor_col0 = 1;
	msg_scoreLabel.cursor_line0 = 1;
	msg_scoreLabel.cursor_col = 1;
	msg_scoreLabel.cursor_line = 1;
	
	msg_hiScoreLabel.state = 1;
	strcpy(msg_hiScoreLabel.text,"HI-SCORE");
	msg_hiScoreLabel.current_char= 0;
	msg_hiScoreLabel.velocity = 8;
	msg_hiScoreLabel.cursor_col0 = 23;
	msg_hiScoreLabel.cursor_line0 = 1;
	msg_hiScoreLabel.cursor_col = 23;
	msg_hiScoreLabel.cursor_line = 1;
	
	msg_score.state = 1;
	strcpy(msg_score.text,"0000");
	msg_score.current_char= 0;
	msg_score.velocity = 8;
	msg_score.cursor_col0 = 2;
	msg_score.cursor_line0 = 4;
	msg_score.cursor_col = 2;
	msg_score.cursor_line = 4;
	
	msg_hiScore.state = 1;
	strcpy(msg_hiScore.text,"0000");
	msg_hiScore.current_char= 0;
	msg_hiScore.velocity = 8;
	msg_hiScore.cursor_col0 = 25;
	msg_hiScore.cursor_line0 = 3;
	msg_hiScore.cursor_col = 25;
	msg_hiScore.cursor_line = 4;
	
	screen3.msg_scoreLabel = msg_scoreLabel;
	screen3.msg_hiScoreLabel = msg_hiScoreLabel;
	screen3.msg_score = msg_score;
	screen3.msg_hiScore = msg_hiScore;
	screen3.state = 1;	
	screen3.msg = 0;
	screen3.velocity = 2;
	screen3.score = 0;
	screen3.hiScore = 0;
	
	game_screen = screen3;
}

void initEndScreen() {
	struct CMessage msg_scoreLabel, msg_score, msg_end, msg_newGame;
	struct Screen4 screen4;
	
	msg_end.state = 1;
	strcpy(msg_end.text,"GAME OVER");
	msg_end.current_char= 0;
	msg_end.velocity = 8;
	msg_end.cursor_col0 = 12;
	msg_end.cursor_line0 = 8;
	msg_end.cursor_col = 12;
	msg_end.cursor_line = 8;
	
	msg_scoreLabel.state = 1;
	strcpy(msg_scoreLabel.text,"SCORE");
	msg_scoreLabel.current_char= 0;
	msg_scoreLabel.velocity = 8;
	msg_scoreLabel.cursor_col0 = 10;
	msg_scoreLabel.cursor_line0 = 12;
	msg_scoreLabel.cursor_col = 10;
	msg_scoreLabel.cursor_line = 12;
	
	msg_score.state = 1;
	strcpy(msg_score.text,"0000");
	msg_score.current_char= 0;
	msg_score.velocity = 8;
	msg_score.cursor_col0 = 19;
	msg_score.cursor_line0 = 12;
	msg_score.cursor_col = 19;
	msg_score.cursor_line = 12;
	
	
	msg_newGame.state = 1;
	strcpy(msg_newGame.text,"PRESS 'A' TO RESTART");
	msg_newGame.current_char= 0;
	msg_newGame.velocity = 2;
	msg_newGame.cursor_col0 = 7;
	msg_newGame.cursor_line0 = 18;
	msg_newGame.cursor_col = 7;
	msg_newGame.cursor_line = 18;

	
	screen4.msg_end = msg_end;
	screen4.msg_scoreLabel = msg_scoreLabel;
	screen4.msg_score = msg_score;
	screen4.msg_newGame = msg_newGame;
	screen4.score = 0;
	
	screen4.state = 1;	
	screen4.msg = 0;
	screen4.velocity = 2;
	
	end_screen = screen4;
}

void refreshGameScreenStatus() {
	game_screen.msg_scoreLabel.state = 1;
	game_screen.msg_hiScoreLabel.state = 1;
	game_screen.msg_score.state = 1;
	game_screen.msg_hiScore.state = 1;
	
	game_screen.msg_scoreLabel.cursor_col=game_screen.msg_scoreLabel.cursor_col0;
	game_screen.msg_scoreLabel.current_char=0;
	
	game_screen.msg_hiScoreLabel.cursor_col=game_screen.msg_hiScoreLabel.cursor_col0;
	game_screen.msg_hiScoreLabel.current_char=0;
	
	game_screen.msg_score.cursor_col=game_screen.msg_score.cursor_col0;
	game_screen.msg_score.current_char=0;
	
	game_screen.msg_hiScore.cursor_col=game_screen.msg_hiScore.cursor_col0;
	game_screen.msg_hiScore.current_char=0;
	
	game_screen.state=1;
	game_screen.score = 0;
	game_screen.msg=0;
}

void refreshEndScreenStatus() {
	end_screen.msg_end.state = 1;
	end_screen.msg_scoreLabel.state = 1;
	end_screen.msg_score.state = 1;
	end_screen.msg_newGame.state = 1;
	
	end_screen.msg_end.cursor_col=end_screen.msg_end.cursor_col0;
	end_screen.msg_end.current_char=0;
	
	end_screen.msg_scoreLabel.cursor_col=end_screen.msg_scoreLabel.cursor_col0;
	end_screen.msg_scoreLabel.current_char=0;
	
	end_screen.msg_score.cursor_col=end_screen.msg_score.cursor_col0;
	end_screen.msg_score.current_char=0;
	
	end_screen.msg_newGame.cursor_col=end_screen.msg_newGame.cursor_col0;
	end_screen.msg_newGame.current_char=0;
	
	
	end_screen.state=1;
	end_screen.msg=0;
}

void addScore(int i) {
	game_screen.score+= i;
}

void finalScore() {
	end_screen.score=game_screen.score;
	game_screen.score=0;
}

void getHiScore() {
	if(game_screen.hiScore<end_screen.score)
		game_screen.hiScore=end_screen.score;
}

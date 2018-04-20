#include <nds.h>
#include "console.h"

struct Screen1 {
	struct CMessage msg_play;
	struct CMessage msg_title;
	struct CMessage msg_scoreHeader;
	struct CMessage msg_scoreMystery;
	struct CMessage msg_score30;
	struct CMessage msg_score20;
	struct CMessage msg_score10;
	struct CMessage msg_next;
	int msg;
	int state;
};

struct Screen2 {
	struct CMessage msg_push;
	struct CMessage msg_arrows;
	struct CMessage msg_buttonX;
	struct CMessage msg_next;
	int msg;
	int state;
};

struct Screen3 {
	struct CMessage msg_scoreLabel;
	struct CMessage msg_hiScoreLabel;
	struct CMessage msg_score;
	struct CMessage msg_hiScore;
	int msg;
	int state;
	int velocity;
	int score;
	int hiScore;
};

struct Screen4 {
	struct CMessage msg_end;
	struct CMessage msg_scoreLabel;
	struct CMessage msg_score;
	struct CMessage msg_newGame;
	int msg;
	int state;
	int velocity;
	int score;
};

int active_screen;
int timer_id;
struct Screen1 title_screen;
struct Screen2 action_screen;
struct Screen3 game_screen;
struct Screen4 end_screen;

void initTitleScreen();
void initActionScreen();
void initGameScreen();
void initEndScreen();

void animateTitleScreen();
void animateActionScreen();
void refreshGameScreen();
void animateEndScreen();

void displayTitleScreen();
void displayActionScreen();
void displayGameScreen();
void displayEndScreen();

void endAnimation();
void addScore(int);
void finalScore();
void getHiScore();

void refreshGameScreenStatus();
void refreshEndScreenStatus();
#include <nds.h>

#define MAX_LINE_SIZE 32
// COLORS STDOUT
#define BLACK   0
#define RED     1
#define GREEN   2
#define WHITE   7

struct CMessage {
	u8 state;                        	// running dialog flag
	char text[MAX_LINE_SIZE]; 			// buffer
	u8 current_char;             		// index of current char in dialog_buffer
	u8 velocity;                    	// writing velocity
	u8 cursor_col0;             		// initial column
	u8 cursor_line0;					// initial line
	u8 cursor_col;                  	// writing column
	u8 cursor_line;                 	// writing line
};

void initConsole();
void setColor(u8 color);
void setText(char *text, struct CMessage *message);
void clearConsole();
void clearLine(u8);
void printMessage(struct CMessage *message);
void printWholeMessage(struct CMessage *message);
void printScoreMessage(struct CMessage *message, int *score);


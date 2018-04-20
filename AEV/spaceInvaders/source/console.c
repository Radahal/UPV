#include <console.h>
#include <string.h>
#include <stdio.h>

void initConsole() {
	consoleDemoInit();
}

void setColor(u8 color) {
    iprintf("\x1b[3%d;1m", color);
}


void setText(char* text,struct CMessage *message) {
	strcpy(message->text, text);
}

void clearConsole() {
	iprintf("\x1b[2J");
}

void clearLine(u8 line) {
	iprintf("\x1b[%d;0H\x1b[K", line);
}

void printMessage(struct CMessage *message) {
	if(message->state) {
		iprintf("\x1b[%d;%dH%c", message->cursor_line, message->cursor_col, message->text[message->current_char]);
		message->current_char++;
		message->cursor_col++;
		message->state = (message->text[message->current_char] != '\0');
	}		
}

void printScoreMessage(struct CMessage *message, int* score) {
	if(message->state) {
		iprintf("\x1b[%d;%dH%4d", message->cursor_line, message->cursor_col, *score);
		message->state = 0;
	}
}



void printWholeMessage(struct CMessage *message) {
	if(message->state) {
		iprintf("\x1b[%d;%dH%s", message->cursor_line, message->cursor_col, message->text);
		message->state = 0;
	}	
}


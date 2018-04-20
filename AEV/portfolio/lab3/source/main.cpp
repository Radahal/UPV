#include <nds.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define WIDTH 256
#define HEIGHT 256


//function prototypes
void clearCanvas();
u16 generateColor();
void saveImage();
void initBackground();
void drawPixel(int,int,u16);
void savePPMImage(char*);

//---------------------------------------------------------------------------------
int main(void) {
//---------------------------------------------------------------------------------
	int keys;
	int x,y;
	u16 color;
	touchPosition touch;

    initBackground(); 
	consoleDemoInit();
	clearCanvas();
	color = RGB15(0,0,0);

	//main loop
	while(1) {
		
		
		scanKeys();
		touchRead(&touch);
		keys = keysDown();
		
		x = touch.px;	
		y = touch.py;
	
		
		drawPixel(x,y,color);
		iprintf("X = %04i, Y = %04i\n", x, y);	
		iprintf("color = %08i", color);	

		if(keys & KEY_A) color = generateColor();
		if(keys & KEY_B) clearCanvas();
		if(keys & KEY_SELECT) saveImage();
		if(keys & KEY_START) break;	
		swiWaitForVBlank();
		consoleClear();

	}
	
	return 0;
	
}

void OnKeyPressed(int key) {
   if(key > 0)
      iprintf("%c", key);
}

void saveImage() {
	//get the name
	char fileName[256];
		
		
	iprintf("\n\nPut the name of the new file:\n");
	Keyboard *kbd = keyboardDemoInit();
	kbd->OnKeyPressed = OnKeyPressed;
	scanf("%s", fileName);
	
	iprintf("\nNew file name%s", fileName);
	//save in the root as ppm file
	savePPMImage(fileName);
	
	
	
}

void clearCanvas() {
	int i;
	for(i = 0; i < WIDTH*HEIGHT; i++)
		VRAM_A[i] = RGB15(31,31,31);
}

void drawPixel(int x,int  y,u16 color) {
	int position = y*HEIGHT+x;
	VRAM_A[position] = color; 
}

u16 generateColor() {
	int r = (abs(rand()) % 32);
	int g = (abs(rand()) % 32);
	int b = (abs(rand()) % 32);
	return RGB15(r,g,b);
}



void initBackground() {
	
	videoSetMode(MODE_FB0);
	vramSetBankA(VRAM_A_LCD);
	//lcdMainOnBottom();
}

void savePPMImage(char* fileName) {
	FILE *fp;	
	fp = fopen("file.ppm", "w+");
   
	//PPM header
	fprintf(fp, "P6\n %d %d 255\n", WIDTH, HEIGHT); 
	//PPM content
	for (int i = 0; i < WIDTH * HEIGHT; i++) {
		fprintf(fp, "%c", VRAM_A[i]); 
	}
	fclose(fp);

}





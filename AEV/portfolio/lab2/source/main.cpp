#include <nds.h>
#include <stdio.h>
#include <stdlib.h>

//-----------------------------


void action1_1() {	
	videoSetMode(MODE_5_2D);
	videoSetModeSub(MODE_0_2D);
	vramSetBankA(VRAM_A_MAIN_BG);

}

void action1_2() {	
	videoSetMode(MODE_5_2D);
	videoSetModeSub(MODE_0_2D);
	vramSetBankA(VRAM_A_MAIN_BG);
	
}

void action1_3() {	
	videoSetMode(MODE_5_2D);
	videoSetModeSub(MODE_0_2D);
	vramSetBankA(VRAM_A_MAIN_BG);
	
}

void action2_1() {	
	videoSetMode(MODE_5_2D);
	videoSetModeSub(MODE_0_2D);
	vramSetBankA(VRAM_A_MAIN_BG);

}

void action2_2() {	
	videoSetMode(MODE_5_2D);
	videoSetModeSub(MODE_0_2D);
	vramSetBankA(VRAM_A_MAIN_BG);
	
}

//-----------------------------



struct Demo
{
	fp go;
	const char* name;
	const char* description;
};

struct Category
{
	const char* name;
	Demo *demos;
	int count;
};

struct Demo submenuA[] =
{ 
	{action1_1, "1.1", "Option 1.1"},
	{action1_2, "1.2", "Option 1.2"},
	{action1_3, "1.3", "Option 1.3"}
	
};

struct Demo submenuB[] =
{ 
	{action2_1, "2.1", "Option 2.1"},
	{action2_2, "2.2", "Option 2.2"}
	
};


struct Category categories[] = 
{
	{"Option A", submenuA, sizeof(submenuA) / sizeof(Demo)},
	{"Option B", submenuB, sizeof(submenuB) / sizeof(Demo)},
	{"Exit", 0, 0},
};

//---------------------------------------------------------------------------------
int main(void) {
//---------------------------------------------------------------------------------
	int keys;

	
	while(1) {
		int selectedCategory = 0;
		int selectedDemo = 0;
		
		bool selected = false;
		
		int catCount = sizeof(categories) / sizeof(Category);
		int demoCount = 0;
		
		videoSetModeSub(MODE_0_2D);
		consoleDemoInit();
		
		while(!selected) {

			scanKeys();
			
			keys = keysDown();
			
			if(keys & KEY_UP) selectedCategory--;
			if(keys & KEY_DOWN) selectedCategory++;
			if(keys & KEY_A) selected = true;
			
			if(selectedCategory < 0) selectedCategory = catCount - 1;
			if(selectedCategory >= catCount) selectedCategory = 0;
			
			swiWaitForVBlank();
			consoleClear();
			for(int ci = 0; ci < catCount; ci++) {
				iprintf("%c%d: %s\n", ci == selectedCategory ? '*' : ' ', ci + 1, categories[ci].name); 		
			}	
		}
		
		selected = false;
		
		demoCount = categories[selectedCategory].count;

		if ( 0 == demoCount ) exit(0);
		
		while(!selected) {

			scanKeys();
			
			keys = keysDown();
			
			if(keys & KEY_UP) selectedDemo--;
			if(keys & KEY_DOWN) selectedDemo++;
			if(keys & KEY_A) selected = true;
			if(keys & KEY_B) break;
			
			if(selectedDemo < 0) selectedDemo = demoCount - 1;
			if(selectedDemo >= demoCount) selectedDemo = 0;
			
			swiWaitForVBlank();
			consoleClear();

			for(int di = 0; di < demoCount; di++) {
				iprintf("%c%d: %s\n", di == selectedDemo ? '*' : ' ', di + 1, categories[selectedCategory].demos[di].name); 		
			}	
		}
		
		if(selected) {
			consoleClear();
			iprintf("Use arrow keys to scroll\nPress 'B' to exit");
			categories[selectedCategory].demos[selectedDemo].go();
		}
	}
	
}




@{{BLOCK(gfx_invader2)

@=======================================================================
@
@	gfx_invader2, 16x16@4, 
@	+ palette 16 entries, not compressed
@	+ 4 tiles not compressed
@	Total size: 32 + 128 = 160
@
@	Time-stamp: 2018-04-16, 20:24:07
@	Exported by Cearn's GBA Image Transmogrifier, v0.8.14
@	( http://www.coranac.com/projects/#grit )
@
@=======================================================================

	.section .rodata
	.align	2
	.global gfx_invader2Tiles		@ 128 unsigned chars
	.hidden gfx_invader2Tiles
gfx_invader2Tiles:
	.word 0x00001100,0x10011001,0x10011001,0x11111001,0x01101111,0x11111111,0x11111110,0x00001100
	.word 0x00000011,0x00001001,0x00001001,0x00001011,0x00001110,0x00001111,0x00000111,0x00000011
	.word 0x00001100,0x00000110,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000
	.word 0x00000011,0x00000110,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000

	.section .rodata
	.align	2
	.global gfx_invader2Pal		@ 32 unsigned chars
	.hidden gfx_invader2Pal
gfx_invader2Pal:
	.hword 0x0000,0x7FFF,0x03E0,0x001F,0x0000,0x0000,0x0000,0x0000
	.hword 0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000

@}}BLOCK(gfx_invader2)

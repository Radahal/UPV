
@{{BLOCK(gfx_invader1)

@=======================================================================
@
@	gfx_invader1, 16x16@4, 
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
	.global gfx_invader1Tiles		@ 128 unsigned chars
	.hidden gfx_invader1Tiles
gfx_invader1Tiles:
	.word 0x00011000,0x00111100,0x01011010,0x11011011,0x11111111,0x00100100,0x01011010,0x10100101
	.word 0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000
	.word 0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000
	.word 0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000

	.section .rodata
	.align	2
	.global gfx_invader1Pal		@ 32 unsigned chars
	.hidden gfx_invader1Pal
gfx_invader1Pal:
	.hword 0x0000,0x7FFF,0x03E0,0x001F,0x0000,0x0000,0x0000,0x0000
	.hword 0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000

@}}BLOCK(gfx_invader1)

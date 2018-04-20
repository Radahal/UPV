
@{{BLOCK(gfx_fire2)

@=======================================================================
@
@	gfx_fire2, 8x8@4, 
@	+ palette 16 entries, not compressed
@	+ 1 tiles not compressed
@	Total size: 32 + 32 = 64
@
@	Time-stamp: 2018-04-16, 20:24:06
@	Exported by Cearn's GBA Image Transmogrifier, v0.8.14
@	( http://www.coranac.com/projects/#grit )
@
@=======================================================================

	.section .rodata
	.align	2
	.global gfx_fire2Tiles		@ 32 unsigned chars
	.hidden gfx_fire2Tiles
gfx_fire2Tiles:
	.word 0x00000010,0x00000010,0x00000010,0x00000010,0x00000010,0x00000111,0x00000111,0x00000010

	.section .rodata
	.align	2
	.global gfx_fire2Pal		@ 32 unsigned chars
	.hidden gfx_fire2Pal
gfx_fire2Pal:
	.hword 0x0000,0x7FFF,0x03E0,0x001F,0x0000,0x0000,0x0000,0x0000
	.hword 0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000

@}}BLOCK(gfx_fire2)

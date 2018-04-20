
@{{BLOCK(gfx_mystery)

@=======================================================================
@
@	gfx_mystery, 16x16@4, 
@	+ palette 16 entries, not compressed
@	+ 4 tiles not compressed
@	Total size: 32 + 128 = 160
@
@	Time-stamp: 2018-04-16, 20:24:08
@	Exported by Cearn's GBA Image Transmogrifier, v0.8.14
@	( http://www.coranac.com/projects/#grit )
@
@=======================================================================

	.section .rodata
	.align	2
	.global gfx_mysteryTiles		@ 128 unsigned chars
	.hidden gfx_mysteryTiles
gfx_mysteryTiles:
	.word 0x33300000,0x33333000,0x33333300,0x30330330,0x30330330,0x33333333,0x33333333,0x00003000
	.word 0x00000333,0x00033333,0x00333333,0x03303303,0x03303303,0x33333333,0x33333333,0x00030000
	.word 0x00003000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000
	.word 0x00030000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000

	.section .rodata
	.align	2
	.global gfx_mysteryPal		@ 32 unsigned chars
	.hidden gfx_mysteryPal
gfx_mysteryPal:
	.hword 0x0000,0x7FFF,0x03E0,0x001F,0x0000,0x0000,0x0000,0x0000
	.hword 0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000

@}}BLOCK(gfx_mystery)

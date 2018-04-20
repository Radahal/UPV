
@{{BLOCK(gfx_ship)

@=======================================================================
@
@	gfx_ship, 16x16@4, 
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
	.global gfx_shipTiles		@ 128 unsigned chars
	.hidden gfx_shipTiles
gfx_shipTiles:
	.word 0x20000000,0x22000000,0x22000000,0x22222220,0x22222222,0x22222222,0x22222222,0x22222222
	.word 0x00000002,0x00000022,0x00000022,0x02222222,0x22222222,0x22222222,0x22222222,0x22222222
	.word 0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000
	.word 0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000

	.section .rodata
	.align	2
	.global gfx_shipPal		@ 32 unsigned chars
	.hidden gfx_shipPal
gfx_shipPal:
	.hword 0x0000,0x7FFF,0x03E0,0x001F,0x0000,0x0000,0x0000,0x0000
	.hword 0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000

@}}BLOCK(gfx_ship)

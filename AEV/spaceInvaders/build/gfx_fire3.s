
@{{BLOCK(gfx_fire3)

@=======================================================================
@
@	gfx_fire3, 8x8@4, 
@	+ palette 16 entries, not compressed
@	+ 1 tiles not compressed
@	Total size: 32 + 32 = 64
@
@	Time-stamp: 2018-04-16, 20:24:07
@	Exported by Cearn's GBA Image Transmogrifier, v0.8.14
@	( http://www.coranac.com/projects/#grit )
@
@=======================================================================

	.section .rodata
	.align	2
	.global gfx_fire3Tiles		@ 32 unsigned chars
	.hidden gfx_fire3Tiles
gfx_fire3Tiles:
	.word 0x00000333,0x00000030,0x00000030,0x00000030,0x00000030,0x00000030,0x00000030,0x00000030

	.section .rodata
	.align	2
	.global gfx_fire3Pal		@ 32 unsigned chars
	.hidden gfx_fire3Pal
gfx_fire3Pal:
	.hword 0x0000,0x7FFF,0x03E0,0x001F,0x0000,0x0000,0x0000,0x0000
	.hword 0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000

@}}BLOCK(gfx_fire3)

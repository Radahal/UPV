
@{{BLOCK(gfx_invader3)

@=======================================================================
@
@	gfx_invader3, 16x16@4, 
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
	.global gfx_invader3Tiles		@ 128 unsigned chars
	.hidden gfx_invader3Tiles
gfx_invader3Tiles:
	.word 0x22200000,0x22222200,0x22222222,0x22222222,0x20002222,0x22222222,0x22220000,0x20022000
	.word 0x00000222,0x00222222,0x22222222,0x22222222,0x22220002,0x22222222,0x00002222,0x00022002
	.word 0x20022000,0x00000222,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000
	.word 0x00022002,0x22200000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000,0x00000000

	.section .rodata
	.align	2
	.global gfx_invader3Pal		@ 32 unsigned chars
	.hidden gfx_invader3Pal
gfx_invader3Pal:
	.hword 0x0000,0x7FFF,0x03E0,0x001F,0x0000,0x0000,0x0000,0x0000
	.hword 0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000,0x0000

@}}BLOCK(gfx_invader3)

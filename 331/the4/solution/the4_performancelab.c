/********************************************************
 * Kernels to be optimized for the CS:APP Performance Lab
 ********************************************************/

#include <stdio.h>
#include <stdlib.h>
#include "defs.h"

/* 
 * Please fill in the following student struct 
 */
team_t team = {
    "2098812",              /* Student ID */

    "Hakan Bostan",     /* full name */
    "e2098812@ceng.metu.edu.tr",  /* email address */

    "",                   /* leave blank */
    ""                    /* leave blank */
};

/***************
 * TRANSPOSE KERNEL
 ***************/

/******************************************************
 * Your different versions of the transpose kernel go here
 ******************************************************/

/* 
 * naive_transpose - The naive baseline version of transpose 
 */
char naive_transpose_descr[64] = "Naive_transpose: Naive baseline implementation";
void naive_transpose(int dim, int *src, int *dst) 
{
    int i, j;

    for (i = 0; i < dim; i++)
        for (j = 0; j < dim; j++)
            dst[j*dim+i] = src[i*dim+j];
}


/* 
 * transpose - Your current working version of transpose
 * IMPORTANT: This is the version you will be graded on
 */
char transpose_descr[64] = "Transpose: Current working version";
void transpose(int dim, int *src, int *dst) 
{

	int blocksize = 16;
	int i,j;
	for(i=0 ; i<dim ; i+=blocksize)
	{
		for(j=0; j<dim; ++j)
		{
			dst[j*dim+i] = src[(i)*dim+j];
			dst[j*dim+i+ 1] = src[(1+i)*dim+j];
			dst[j*dim+i+ 2] = src[(2+i)*dim+j];
			dst[j*dim+i+ 3] = src[(3+i)*dim+j];
			dst[j*dim+i+ 4] = src[(4+i)*dim+j];
			dst[j*dim+i+ 5] = src[(5+i)*dim+j];
			dst[j*dim+i+ 6] = src[(6+i)*dim+j];
			dst[j*dim+i+ 7] = src[(7+i)*dim+j];
			dst[j*dim+i+ 8] = src[(8+i)*dim+j];
			dst[j*dim+i+ 9] = src[(9+i)*dim+j];
			dst[j*dim+i+ 10] = src[(10+i)*dim+j];
			dst[j*dim+i+ 11] = src[(11+i)*dim+j];
			dst[j*dim+i+ 12] = src[(12+i)*dim+j];
			dst[j*dim+i+ 13] = src[(13+i)*dim+j];
			dst[j*dim+i+ 14] = src[(14+i)*dim+j];
			dst[j*dim+i+ 15] = src[(15+i)*dim+j];
		}
	}

}

/*********************************************************************
 * register_transpose_functions - Register all of your different versions
 *     of the transpose kernel with the driver by calling the
 *     add_transpose_function() for each test function. When you run the
 *     driver program, it will test and report the performance of each
 *     registered test function.  
 *********************************************************************/

void register_transpose_functions() 
{
    add_transpose_function(&naive_transpose, naive_transpose_descr);   
    add_transpose_function(&transpose, transpose_descr);   

    /* ... Register additional test functions here */
}


/***************
 * CONVERT KERNEL
 **************/


/******************************************************
 * Your different versions of the col_convert kernel go here
 ******************************************************/

/*
 * naive_col_convert - The naive baseline version of col_convert 
 */
char naive_col_convert_descr[64] = "Naive_col_convert: Naive baseline implementation";
void naive_col_convert(int dim, int *G) 
{
    int i, j;
    for (i = 0; i < dim; i++)
        for (j = 0; j < dim; j++)
            G[j*dim+i] = G[j*dim+i] || G[i*dim+j];
}



/*
 * col_convert - Your current working version of col_convert. 
 * IMPORTANT: This is the version you will be graded on
 */
char col_convert_descr[64] = "Col_convert: Current working version";
void col_convert(int dim, int *G)
{
	int i,j;
	int blocksize =8;
	for(i=0; i<dim ; i+=blocksize)
	{
		for(j=i+1;j<dim;++j)
		{
				G[j*dim+i] = G[j*dim+i] | G[i*dim+j];
				G[i*dim+j] = G[j*dim+i];

				G[j*dim+i+ 1] = G[j*dim+i+1] | G[(i+1)*dim+j];
				G[(i+1)*dim+j] = G[j*dim+i+1];

				G[j*dim+i+ 2] = G[j*dim+i+2] | G[(i+2)*dim+j];
				G[(i+2)*dim+j] = G[j*dim+i+2];

				G[j*dim+i+ 3] = G[j*dim+i+3] | G[(i+3)*dim+j];
				G[(i+3)*dim+j] = G[j*dim+i+3];

				G[j*dim+i+ 4] = G[j*dim+i+4] | G[(i+4)*dim+j];
				G[(i+4)*dim+j] = G[j*dim+i+4];

				G[j*dim+i+ 5] = G[j*dim+i+5] | G[(i+5)*dim+j];
				G[(i+5)*dim+j] = G[j*dim+i+5];

				G[j*dim+i+ 6] = G[j*dim+i+6] | G[(i+6)*dim+j];
				G[(i+6)*dim+j] = G[j*dim+i+6];

				G[j*dim+i+ 7] = G[j*dim+i+7] | G[(i+7)*dim+j];
				G[(i+7)*dim+j] = G[j*dim+i+7];

				/*G[j*dim+i+ 8] = G[j*dim+i+8] | G[(i+8)*dim+j];
				G[(i+8)*dim+j] = G[j*dim+i+8];

				G[j*dim+i+ 9] = G[j*dim+i+9] | G[(i+9)*dim+j];
				G[(i+9)*dim+j] = G[j*dim+i+9];

				G[j*dim+i+ 10] = G[j*dim+i+10] | G[(i+10)*dim+j];
				G[(i+10)*dim+j] = G[j*dim+i+10];

				G[j*dim+i+ 11] = G[j*dim+i+11] | G[(i+11)*dim+j];
				G[(i+11)*dim+j] = G[j*dim+i+11];

				G[j*dim+i+ 12] = G[j*dim+i+12] | G[(i+12)*dim+j];
				G[(i+12)*dim+j] = G[j*dim+i+12];

				G[j*dim+i+ 13] = G[j*dim+i+13] | G[(i+13)*dim+j];
				G[(i+13)*dim+j] = G[j*dim+i+13];

				G[j*dim+i+ 14] = G[j*dim+i+14] | G[(i+14)*dim+j];
				G[(i+14)*dim+j] = G[j*dim+i+14];

				G[j*dim+i+ 15] = G[j*dim+i+15] | G[(i+15)*dim+j];
				G[(i+15)*dim+j] = G[j*dim+i+15];*/
		}
	}
}


/********************************************************************* 
 * register_col_convert_functions - Register all of your different versions
 *     of the col_convert kernel with the driver by calling the
 *     add_col_convert_function() for each test function.  When you run the
 *     driver program, it will test and report the performance of each
 *     registered test function.  
 *********************************************************************/


void register_col_convert_functions() {
    add_col_convert_function(&naive_col_convert, naive_col_convert_descr);
    add_col_convert_function(&col_convert, col_convert_descr);

    /* ... Register additional test functions here */
}


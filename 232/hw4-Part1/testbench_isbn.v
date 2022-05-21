`timescale 1ns / 1ps
module testbench_isbn(
    );
	reg CLK;
	reg [11:0] isbnInput;
	reg Mode; //0:read, 1:write
	reg [3:0] isbnAddress;
	wire  [11:0] isbnOutput;
	integer result=0;

	integer i;
	integer err=0;
	//instances
	ISBN_RAM ins( isbnInput , CLK , Mode , isbnAddress , isbnOutput );	

	//Set initial values
	initial CLK = 0;	
	always #5 CLK = ~CLK;
  
  initial begin
	$display("Starting Testbench");

	  //read initial values
		#1
		$display("Reading Initial Values");
		Mode=1'b0;			
		for(i=0;i<=15;i=i+1)
		begin
			isbnAddress = i;
			#10;
			if (isbnOutput!==12'b111111111101)
			begin
				$display("time:",$time,":Error in initial value, address %b output %b should be 9'b111111111",isbnAddress,isbnOutput);
				err=1;
			end
		end
		if (err==0) result=result+9;
		//write operation: inputs that have 1-bit error in one of the data bits.
		#1;		
		Mode=1'b1; /*WRITE MODE*/		
		err=0;
		
		isbnAddress=0;
		isbnInput=12'b001011111110;
		#10;


		isbnAddress=14;
		isbnInput=12'b010101111100;
		#10;
		
		isbnAddress=15;
		isbnInput=12'b010101111101;
		#10;

		isbnAddress=7;
		isbnInput=12'b001111100111;
		#10;

		//read operation
		$display($time,"Read memory values");
		#1
		Mode=1'b0;	/*READ MODE*/ 		

		isbnAddress=	0;
		#9;
		if(isbnOutput===12'b001011111110) result=result+9;
		 else $display("time:",$time,":Error in result. For isbnInput:%b,  isbnOutput:%b should be %b",isbnInput,isbnOutput,12'b001011111110);
		#1;

		isbnAddress=	14;
		#9;
		if(isbnOutput===12'b010101111100) result=result+9;
		 else $display("time:",$time,":Error in result. For isbnInput:%b,  isbnOutput:%b should be %b",isbnInput,isbnOutput,12'b010101111100);
		#1;

		isbnAddress=	15;
		#9;
		if(isbnOutput===12'b000000000000) result=result+9;
		 else $display("time:",$time,":Error in result. For isbnInput:%b,  isbnOutput:%b should be %b",isbnInput,isbnOutput,12'b000000000000);
		#1;
		
		isbnAddress=	7;
		#9;
		if(isbnOutput===12'b000000000000) result=result+9;
		 else $display("time:",$time,":Error in result. For isbnInput:%b,  isbnOutput:%b should be %b",isbnInput,isbnOutput,12'b000000000000);
		#1;

		
		$display("Result is:%d",result);
		$finish;
	end

endmodule
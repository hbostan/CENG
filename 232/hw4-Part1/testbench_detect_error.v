`timescale 1ns / 1ps
module testbench_detect_error(
    );
	reg [1:12] decInput;
	wire [1:9] decOutput;
	integer result=0;
	//instances
	detect_error ins( decInput,   decOutput);	
	

	initial begin
	$display("Starting Testbench");

	 decInput=12'b111111111101 ;
	 #1;
	 if (decOutput===9'b111111111) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b111111111);

	 decInput=12'b000000011001 ;
	 #1;
	 if (decOutput===9'b000000011) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000000011);

	 decInput=12'b000001001111 ;
	 #1;
	 if (decOutput===9'b000001001) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000001001);

	 decInput=12'b000001011111 ;
	 #1;
	 if (decOutput===9'b000001011) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000001011);

	 decInput=12'b000010001110 ;
	 #1;
	 if (decOutput===9'b000010001) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000010001);

	 decInput=12'b000011001100 ;
	 #1;
	 if (decOutput===9'b000011001) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000011001);

	 decInput=12'b000100010100 ;
	 #1;
	 if (decOutput===9'b000100010) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000100010);

	 decInput=12'b000101001011 ;
	 #1;
	 if (decOutput===9'b000101001) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000101001);

	 decInput=12'b001001110000 ;
	 #1;
	 if (decOutput===9'b001001110) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b001001110);

	 decInput=12'b001011111110 ;
	 #1;
	 if (decOutput===9'b001011111) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b001011111);

	 decInput=12'b001101110100 ;
	 #1;
	 if (decOutput===9'b001101110) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b001101110);

	 decInput=12'b001111100001 ;
	 #1;
	 if (decOutput===9'b001111100) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b001111100);

	 decInput=12'b010101111100 ;
	 #1;
	 if (decOutput===9'b010101111) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b010101111);

	 decInput=12'b111111111110 ;
	 #1;
	 if (decOutput===9'b000000000) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000000000);

	 decInput=12'b000000011010 ;
	 #1;
	 if (decOutput===9'b000000000) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000000000);

	 decInput=12'b000001001000 ;
	 #1;
	 if (decOutput===9'b000000000) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000000000);

	 decInput=12'b000001011000 ;
	 #1;
	 if (decOutput===9'b000000000) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000000000);

	 decInput=12'b000010001111 ;
	 #1;
	 if (decOutput===9'b000000000) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000000000);

	 decInput=12'b000011001101 ;
	 #1;
	 if (decOutput===9'b000000000) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000000000);

	 decInput=12'b000100010101 ;
	 #1;
	 if (decOutput===9'b000000000) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000000000);

	 decInput=12'b000101001100 ;
	 #1;
	 if (decOutput===9'b000000000) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000000000);

	 decInput=12'b001001110001 ;
	 #1;
	 if (decOutput===9'b000000000) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000000000);

	 decInput=12'b001011111111 ;
	 #1;
	 if (decOutput===9'b000000000) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000000000);

	 decInput=12'b001101110101 ;
	 #1;
	 if (decOutput===9'b000000000) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000000000);

	 decInput=12'b001111100010 ;
	 #1;
	 if (decOutput===9'b000000000) result=result+1;
		else $display("time:",$time,":Error in result. For	 decInput:%b,  decOutput:%b should be %b",decInput,decOutput,9'b000000000);


	

	 $display("Result is:%d",result);
	 $finish;
	end
		
endmodule
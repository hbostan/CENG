`timescale 1ns / 1ps
module testbench_add_checksum(
    );

	reg [1:9] acInput;
	wire [1:12] acOutput;
	integer result=0;
	
	//instances
	add_checksum ins( acInput,   acOutput);	
	
	initial begin
	$display("Starting Testbench");
	
	
	#1;
	 acInput=9'b011101000 ;
	#1;
	 if (acOutput===12'b011101000111) result=result+2;
		else $display("time:",$time,":Error in result. For acInput:%b,  acOutput:%b should be %b",acInput,acOutput,12'b011101000111);

	#1;
	 acInput=9'b101011111 ;
	#1;
	 if (acOutput===12'b101011111111) result=result+2;
		else $display("time:",$time,":Error in result. For acInput:%b,  acOutput:%b should be %b",acInput,acOutput,12'b101011111111);

	#1;
	 acInput=9'b101001011 ;
	#1;
	 if (acOutput===12'b101001011011) result=result+2;
		else $display("time:",$time,":Error in result. For acInput:%b,  acOutput:%b should be %b",acInput,acOutput,12'b101001011011);

	#1;
	 acInput=9'b011010101 ;
	#1;
	 if (acOutput===12'b011010101010) result=result+2;
		else $display("time:",$time,":Error in result. For acInput:%b,  acOutput:%b should be %b",acInput,acOutput,12'b011010101010);

	#1;
	 acInput=9'b011110010 ;
	#1;
	 if (acOutput===12'b011110010110) result=result+2;
		else $display("time:",$time,":Error in result. For acInput:%b,  acOutput:%b should be %b",acInput,acOutput,12'b011110010110);

	#1;
	 acInput=9'b010001100 ;
	#1;
	 if (acOutput===12'b010001100111) result=result+2;
		else $display("time:",$time,":Error in result. For acInput:%b,  acOutput:%b should be %b",acInput,acOutput,12'b010001100111);

	#1;
	 acInput=9'b011011111 ;
	#1;
	 if (acOutput===12'b011011111000) result=result+2;
		else $display("time:",$time,":Error in result. For acInput:%b,  acOutput:%b should be %b",acInput,acOutput,12'b011011111000);

	#1;
	 acInput=9'b101100000 ;
	#1;
	 if (acOutput===12'b101100000000) result=result+2;
		else $display("time:",$time,":Error in result. For acInput:%b,  acOutput:%b should be %b",acInput,acOutput,12'b101100000000);

	#1;
	 acInput=9'b001101111 ;
	#1;
	 if (acOutput===12'b001101111101) result=result+2;
		else $display("time:",$time,":Error in result. For acInput:%b,  acOutput:%b should be %b",acInput,acOutput,12'b001101111101);

	#1;
	 acInput=9'b101001110 ;
	#1;
	 if (acOutput===12'b101001110001) result=result+2;
		else $display("time:",$time,":Error in result. For acInput:%b,  acOutput:%b should be %b",acInput,acOutput,12'b101001110001);

	
	
	
	

		$display("Result is:%d",result);
		$finish;
	end
endmodule
`timescale 1ns / 1ps

module ac(
    input a,
    input c,
    input clk,
    output reg q
    );
    
    initial begin
        q = 0;
    end

    // write your code here
	 
	 always @(posedge clk)
		begin
		case({a,c})
			{1'b0,1'b0}: begin q<=  1'b1; end
			{1'b1,1'b1}: begin q<=  1'b1; end
			{1'b0,1'b1}: begin q<=  !q; end
			{1'b1,1'b0}: begin q<=  q; end
		endcase
		end

endmodule


module ic3031(
    input a0, 
    input c0, 
    input a1, 
    input c1, 
    input clk, 
    output q0, 
    output q1, 
    output y
    );
    
    // write your code here
	 	ac ff1(.a(a0), .c(c0), .clk(clk), .q(q0));
		ac ff2(.a(a1), .c(c1), .clk(clk), .q(q1));
		assign y = (q0 ~^ q1);
	
	 
	 

endmodule

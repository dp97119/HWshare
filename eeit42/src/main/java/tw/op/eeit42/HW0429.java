package tw.op.eeit42;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/HW0429")
public class HW0429 extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int x, y, result, n;
		try {
			String strX = request.getParameter("x");
			String strY = request.getParameter("y");
			String[] cals = request.getParameterValues("cal");
			
			x = Integer.parseInt(strX);
			y = Integer.parseInt(strY);
			result = 0;
			n = 0;
			
			for (String c : cals) {
				switch (c){
					case "1":
						result = x + y;
						break;
					case "2":
						result = x - y;
						break;
					case "3":
						result = x * y;
						break;
					case "4":
						result = x / y;
						n = x % y;
						break;
					default:
						x = y = result = n = 0;
						break;
				}
			}
		}catch(Exception e) {
			x = y = result = n = 0;
		}
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<meta charset='UTF-8' />");
		out.println("<form action='HW0429'>");
		out.println("<input name='x' value='"+ x +"'>");
		out.println("<select name='cal'>");
		out.println("<option value='1'>+</option>");
		out.println("<option value='2'>-</option>");
		out.println("<option value='3'>x</option>");
		out.println("<option value='4'>/</option>");
		out.println("</select>");
		out.println("<input name='y' value='"+ y +"'>");
		out.println("<input type='submit' value='='>");
		out.print(result);
		out.printf(n != 0? "餘"+n :"");
		out.println("</form>");
		
		response.flushBuffer();
	}

}

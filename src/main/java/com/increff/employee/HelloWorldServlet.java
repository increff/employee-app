package com.increff.employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HelloWorld", description = "sample servlet", urlPatterns = { "/employee" })
public class HelloWorldServlet extends HttpServlet {

	private static final long serialVersionUID = 1042412442755277478L;
	private static EmployeeHibernateApi api;

	static {
		HibernateUtil.configure();
		api = new EmployeeHibernateApi();
	}

	@Override
	// Retrieving
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<h1>Increff Employee</h1>");

		int id = Integer.valueOf(req.getParameter("id"));
		EmployeePojo p = null;
		try {
			p = api.select(id);
		} catch (SQLException e) {
			throw new ServletException("Error retrieving object", e);
		}

		out.println("<br>");
		out.println("Name: " + p.getName());
		out.println("<br>");
		out.println("Age: " + p.getAge());
		out.println("<br>");
		out.println("Id: " + p.getId());

	}

	// Creating
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String id = req.getParameter("id");
		String age = req.getParameter("age");

		EmployeePojo p = new EmployeePojo();
		p.setAge(Integer.valueOf(age));
		p.setId(Integer.valueOf(id));
		p.setName(name);

		try {
			api.insert(p);
		} catch (SQLException e) {
			throw new ServletException("Error saving object", e);
		}
	}

	// Updating
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name");
		String id = req.getParameter("id");
		String age = req.getParameter("age");

		EmployeePojo p = new EmployeePojo();
		p.setAge(Integer.valueOf(age));
		p.setId(Integer.valueOf(id));
		p.setName(name);

		try {
			api.update(p.getId(), p);
		} catch (SQLException e) {
			throw new ServletException("Error updating object", e);
		}
		
	}

	// Deleting
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter out = resp.getWriter();
		out.println("<h1>Employee Deleted</h1>");
		int id = Integer.valueOf(req.getParameter("id"));
		try {
			api.delete(id);
		} catch (SQLException e) {
			throw new ServletException("Error retrieving object", e);
		}

	}
}

package com.emodoki.rest.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet(urlPatterns = "/imgServe")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = null;
		try {
			String code = request.getParameter("c");
			String realPath="";
			if (code != null && !code.equals("")) {
				int index = request.getSession().getServletContext().getRealPath("").lastIndexOf("/");
				if (index > 0) {
					realPath = request.getSession().getServletContext().getRealPath("").substring(0, index);
				} else {
					index = request.getSession().getServletContext().getRealPath("").lastIndexOf("\\");
					realPath = request.getSession().getServletContext().getRealPath("").substring(0, index);
				}				
				String path = realPath + "/challengeImages/"+code;
				File file = new File(path);

				out = response.getOutputStream();
				BufferedImage img = ImageIO.read(file);
				ImageIO.write(img, "png", out);
				out.flush();
				img.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

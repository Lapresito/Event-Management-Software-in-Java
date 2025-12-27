package controllers;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cerrar-sesion")
public class Logout extends HttpServlet {
  private static final long serialVersionUID = 1L;

  // Maneja peticiones GET (por ejemplo, desde un link en la navbar)
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    cerrarSesion(request, response);
  }

  // Maneja peticiones POST (por ejemplo, desde un formulario con method="post")
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    cerrarSesion(request, response);
  }

  // Lógica común para ambos métodos
  private void cerrarSesion(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    HttpSession session = request.getSession(false); // false = no crear si no existe
    if (session != null) {
      session.invalidate(); // elimina todos los atributos de sesión
    }

    // Redirige al inicio o donde prefieras
    response.sendRedirect(request.getContextPath() + "/home");
  }
}

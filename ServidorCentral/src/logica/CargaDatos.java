
package logica;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * javadoc commet.
 */
public class CargaDatos {
  
  Fabrica fabrica = Fabrica.getInstance();
  IEventosController eventosController = fabrica.getIControladorEventos();
  ManejadorUsuarios mUs = ManejadorUsuarios.getinstance();
  ManejadorEventos mEv = ManejadorEventos.getInstancia();
  ManejadorInstituciones mIv = ManejadorInstituciones.getInstance();
  
  IUsuariosController uctrl = fabrica.getIUsuariosController();
  IEventosController ectrl = fabrica.getIControladorEventos();
  IInstitucionesController ictrl = fabrica.getIInstitucionesController();

  /**
   * javadoc commet.
   */
  public CargaDatos() {
    this.fabrica = Fabrica.getInstance();
  }

  /**
   * javadoc commet.
   */
  public boolean cargar() {

    try {
      // ----------------- INSTITUCIONES -----------------
      ictrl.altaInstitucion("Facultad de Ingeniería",
          "Facultad de Ingeniería de la Universidad de la República", "https://www.fing.edu.uy",
          "");
      ictrl.altaInstitucion("ORT Uruguay", "Universidad privada enfocada en tecnología y gestión",
          "https://ort.edu.uy", "");
      ictrl.altaInstitucion("Universidad Católica del Uruguay",
          "Institución de educación superior privada", "https://ucu.edu.uy", "");
      ictrl.altaInstitucion("Antel", "Empresa estatal de telecomunicaciones",
          "https://antel.com.uy", "");
      ictrl.altaInstitucion("Agencia Nacional de Investigación e Innovación (ANII)",
          "Fomenta la investigación y la innovación en Uruguay", "https://anii.org.uy", "");

      // ----------------- ORGANIZADORES -----------------
      uctrl.altaOrganizador("miseventos", "contacto@miseventos.com", "MisEventos",
          "Empresa de organización de eventos.", "", "22miseventos");
      uctrl.ingresarSitioWeb("miseventos", "https://miseventos.com");

      uctrl.altaOrganizador("techcorp", "info@techcorp.com", "Corporación Tecnológica",
          "Empresa líder en tecnologías de la información.", "", "tech25corp");

      uctrl.altaOrganizador("imm", "contacto@imm.gub.uy", "Intendencia de Montevideo",
          "Gobierno departamental de Montevideo.", "", "imm2025");
      uctrl.ingresarSitioWeb("imm", "https://montevideo.gub.uy");

      uctrl.altaOrganizador("udelar", "contacto@udelar.edu.uy", "Universidad de la República",
          "Universidad pública de Uruguay.", "", "25udelar");
      uctrl.ingresarSitioWeb("udelar", "https://udelar.edu.uy");

      uctrl.altaOrganizador("mec", "mec@mec.gub.uy", "Ministerio de Educación y Cultura",
          "Institución pública promotora de cultura.", "", "mec2025ok");
      uctrl.ingresarSitioWeb("mec", "https://mec.gub.uy");

      // ----------------- ASISTENTES -----------------
      uctrl.altaAsistente("atorres", "atorres@gmail.com", "Ana", "Torres",
          LocalDate.of(1990, 5, 12), "", "123.torres");
      uctrl.ingresarInstitucion("atorres", "Facultad de Ingeniería"); // INS01

      uctrl.altaAsistente("msilva", "martin.silva@fing.edu.uy", "Martin", "Silva",
          LocalDate.of(1987, 8, 21), "", "msilva2025");
      uctrl.ingresarInstitucion("msilva", "Facultad de Ingeniería"); // INS01

      uctrl.altaAsistente("sofirod", "srodriguez@outlook.com", "Sofia", "Rodriguez",
          LocalDate.of(1995, 2, 3), "", "srod.abc1");
      uctrl.ingresarInstitucion("sofirod", "Universidad Católica del Uruguay"); // INS03

      uctrl.altaAsistente("vale23", "valentina.costa@mail.com", "Valentina", "Costa",
          LocalDate.of(1992, 12, 1), "", "valen11c");
      // sin institución

      uctrl.altaAsistente("luciag", "lucia.garcia@mail.com", "Lucía", "García",
          LocalDate.of(1993, 11, 9), "", "garcia.22l");
      // sin institución

      uctrl.altaAsistente("andrearod", "andrea.rod@mail.com", "Andrea", "Rodríguez",
          LocalDate.of(2000, 6, 10), "", "rod77and");
      uctrl.ingresarInstitucion("andrearod",
          "Agencia Nacional de Investigación e Innovación (ANII)"); // INS05

      uctrl.altaAsistente("AnaG", "ana.gomez@hotmail.com", "Ana", "Gómez",
          LocalDate.of(1998, 3, 15), "", "gomez88a");
      // sin institución

      uctrl.altaAsistente("JaviL", "javier.lopez@outlook.com", "Javier", "López",
          LocalDate.of(1995, 7, 22), "", "jl99lopez");
      // sin institución

      uctrl.altaAsistente("MariR", "maria.rodriguez@gmail.com", "María", "Rodríguez",
          LocalDate.of(2000, 11, 10), "", "maria55r");
      // sin institución

      uctrl.altaAsistente("SofiM", "sofia.martinez@yahoo.com", "Sofía", "Martínez",
          LocalDate.of(1997, 2, 5), "", "smarti99z");
      // sin institución

      // --------------------- CATEGORIAS -----------------------
      ectrl.altaCategoria("Tecnología");
      ectrl.altaCategoria("Innovación");
      ectrl.altaCategoria("Literatura");
      ectrl.altaCategoria("Cultura");
      ectrl.altaCategoria("Música");
      ectrl.altaCategoria("Deporte");
      ectrl.altaCategoria("Salud");
      ectrl.altaCategoria("Entretenimiento");
      ectrl.altaCategoria("Agro");
      ectrl.altaCategoria("Negocios");
      ectrl.altaCategoria("Moda");
      ectrl.altaCategoria("Investigación");

      // ------------------------ EVENTOS ----------------------------
      List<String> categoriasEvento;

      // EV01 - Conferencia de Tecnología
      categoriasEvento = new ArrayList<>();
      categoriasEvento.add("Tecnología");
      categoriasEvento.add("Innovación");
      boolean evento1 = ectrl.altaEvento("Conferencia de Tecnología",
          "Evento sobre innovación tecnológica", "CONFTEC", categoriasEvento,
          LocalDate.of(2025, 1, 10), "");

      // EV02 - Feria del Libro
      categoriasEvento = new ArrayList<>();
      categoriasEvento.add("Literatura");
      categoriasEvento.add("Cultura");
      boolean evento2 = ectrl.altaEvento("Feria del Libro", "Encuentro anual de literatura",
          "FERLIB", categoriasEvento, LocalDate.of(2025, 2, 1), "");

      // EV03 - Montevideo Rock
      categoriasEvento = new ArrayList<>();
      categoriasEvento.add("Cultura");
      categoriasEvento.add("Música");
      boolean evento3 = ectrl.altaEvento("Montevideo Rock",
          "Festival de rock con artistas nacionales e internacionales", "MONROCK", categoriasEvento,
          LocalDate.of(2023, 3, 15), "");

      // EV04 - Maratón de Montevideo
      categoriasEvento = new ArrayList<>();
      categoriasEvento.add("Deporte");
      categoriasEvento.add("Salud");
      boolean evento4 = ectrl.altaEvento("Maratón de Montevideo",
          "Competencia deportiva anual en la capital", "MARATON", categoriasEvento,
          LocalDate.of(2022, 1, 1), "");

      // EV05 - Montevideo Comics
      categoriasEvento = new ArrayList<>();
      categoriasEvento.add("Cultura");
      categoriasEvento.add("Entretenimiento");
      boolean evento5 = ectrl.altaEvento("Montevideo Comics",
          "Convención de historietas, cine y cultura geek", "COMICS", categoriasEvento,
          LocalDate.of(2024, 4, 10), "");

      // EV06 - Expointer Uruguay
      categoriasEvento = new ArrayList<>();
      categoriasEvento.add("Agro");
      categoriasEvento.add("Negocios");
      boolean evento6 = ectrl.altaEvento("Expointer Uruguay",
          "Exposición internacional agropecuaria y ganadera", "EXPOAGRO", categoriasEvento,
          LocalDate.of(2024, 12, 12), "");

      // EV07 - Montevideo Fashion Week
      categoriasEvento = new ArrayList<>();
      categoriasEvento.add("Cultura");
      categoriasEvento.add("Moda");
      boolean evento7 = ectrl.altaEvento("Montevideo Fashion Week",
          "Pasarela de moda uruguaya e internacional", "MFASHION", categoriasEvento,
          LocalDate.of(2025, 7, 20), "");
      
      //EV08 - Global
      categoriasEvento = new ArrayList<>();
      categoriasEvento.add("Cultura");
      boolean evento8 = ectrl.altaEvento("Global",
          "Aventureros en grupo", "GBL", categoriasEvento,
          LocalDate.of(2025, 1, 1), "");
      ectrl.finalizarEvento("Global");
      

      if (!evento1 || !evento2 || !evento3 || !evento4 || !evento5 || !evento6 || !evento7 || !evento8) {
        throw new Exception("Error al dar de alta uno o más eventos");
      }

      // --------------------------------- EDICIONES
      // ----------------------------------
      // altaEdicion(String nombreEvento, String nicknameOrganizador, String
      // nombreEdicion, String sigla, String ciudad, String pais, LocalDate fechaIni,
      // LocalDate fechaFin, LocalDate fechaAlta)
      ectrl.altaEdicion("Montevideo Rock", "imm", "Montevideo Rock 2025", "MONROCK25", "Montevideo",
          "Uruguay", LocalDate.of(2025, 11, 20), LocalDate.of(2025, 11, 22),
          LocalDate.of(2025, 3, 12), "");
      ectrl.aceptarEdicion("Montevideo Rock 2025");

      ectrl.altaEdicion("Maratón de Montevideo", "imm", "Maratón de Montevideo 2025", "MARATON25",
          "Montevideo", "Uruguay", LocalDate.of(2025, 9, 14), LocalDate.of(2025, 9, 14),
          LocalDate.of(2025, 2, 5), "");
      ectrl.aceptarEdicion("Maratón de Montevideo 2025");

      ectrl.altaEdicion("Maratón de Montevideo", "imm", "Maratón de Montevideo 2024", "MARATON24",
          "Montevideo", "Uruguay", LocalDate.of(2024, 9, 14), LocalDate.of(2024, 9, 14),
          LocalDate.of(2024, 4, 21), "");
      ectrl.aceptarEdicion("Maratón de Montevideo 2024");

      ectrl.altaEdicion("Maratón de Montevideo", "imm", "Maratón de Montevideo 2022", "MARATON22",
          "Montevideo", "Uruguay", LocalDate.of(2022, 9, 14), LocalDate.of(2022, 9, 14),
          LocalDate.of(2022, 5, 21), "");
      ectrl.rechazarEdicion("Maratón de Montevideo 2022");

      ectrl.altaEdicion("Montevideo Comics", "miseventos", "Montevideo Comics 2024", "COMICS24",
          "Montevideo", "Uruguay", LocalDate.of(2024, 7, 18), LocalDate.of(2024, 7, 21),
          LocalDate.of(2024, 6, 20), "");
      ectrl.aceptarEdicion("Montevideo Comics 2024");

      ectrl.altaEdicion("Montevideo Comics", "miseventos", "Montevideo Comics 2025", "COMICS25",
          "Montevideo", "Uruguay", LocalDate.of(2025, 8, 4), LocalDate.of(2025, 8, 6),
          LocalDate.of(2025, 7, 4), "");
      ectrl.aceptarEdicion("Montevideo Comics 2025");

      ectrl.altaEdicion("Expointer Uruguay", "miseventos", "Expointer Uruguay 2025", "EXPOAGRO25",
          "Durazno", "Uruguay", LocalDate.of(2025, 9, 11), LocalDate.of(2025, 9, 17),
          LocalDate.of(2025, 2, 1), "");

      // EV01 (Conferencia de Tecnología) — EDEV08, EDEV09, EDEV10
      ectrl.altaEdicion("Conferencia de Tecnología", "udelar", "Tecnología Punta del Este 2026",
          "CONFTECH26", "Punta del Este", "Uruguay", LocalDate.of(2026, 4, 6),
          LocalDate.of(2026, 4, 10), LocalDate.of(2025, 8, 1), "");
      ectrl.aceptarEdicion("Tecnología Punta del Este 2026");

      ectrl.altaEdicion("Conferencia de Tecnología", "techcorp", "Mobile World Congress 2025",
          "MWC", "Barcelona", "España", LocalDate.of(2025, 12, 12), LocalDate.of(2025, 12, 15),
          LocalDate.of(2025, 8, 21), "");
      ectrl.aceptarEdicion("Mobile World Congress 2025");

      ectrl.altaEdicion("Conferencia de Tecnología", "techcorp", "Web Summit 2026", "WS26",
          "Lisboa", "Portugal", LocalDate.of(2026, 1, 13), LocalDate.of(2026, 2, 1),
          LocalDate.of(2025, 6, 4), "");
      ectrl.aceptarEdicion("Web Summit 2026");

      ectrl.altaEdicion("Montevideo Fashion Week", "techcorp", "Montevideo Fashion Week 2026",
          "MFW26", "Nueva York", "Estados Unidos", LocalDate.of(2026, 2, 16),
          LocalDate.of(2026, 2, 20), LocalDate.of(2025, 10, 2), "");
      
      ectrl.altaEdicion("Global", "miseventos", "Descubre la Magia de Machu Picchu",
              "MFW26", "Cusco", "Perú", LocalDate.of(2025, 11, 10),
              LocalDate.of(2025, 11, 30), LocalDate.of(2025, 8, 7), ""); 
      ectrl.aceptarEdicion("Descubre la Magia de Machu Picchu");
      // ------------------------------- TIPOS DE REGISTRO
      // ----------------------------------------
      // altaTipoDeRegistro(String nombreTipo, String nombreEdicion, String
      // descripcion, int costo, int cupo)
      ectrl.altaTipoDeRegistro("General", "Montevideo Rock 2025",
          "Acceso general a Montevideo Rock (2 días)", 1500, 2000);
      ectrl.altaTipoDeRegistro("VIP", "Montevideo Rock 2025",
          "Incluye backstage + acceso preferencial", 4000, 200);

      ectrl.altaTipoDeRegistro("Corredor 42K", "Maratón de Montevideo 2025",
          "Inscripción a la maratón completa", 1200, 499);
      ectrl.altaTipoDeRegistro("Corredor 21K", "Maratón de Montevideo 2025",
          "Inscripción a la media maratón", 800, 700);
      ectrl.altaTipoDeRegistro("Corredor 10K", "Maratón de Montevideo 2025",
          "Inscripción a la carrera 10K", 500, 1000);

      ectrl.altaTipoDeRegistro("Corredor 42K", "Maratón de Montevideo 2024",
          "Inscripción a la maratón completa", 1000, 300);
      ectrl.altaTipoDeRegistro("Corredor 21K", "Maratón de Montevideo 2024",
          "Inscripción a la media maratón", 500, 500);

      ectrl.altaTipoDeRegistro("Corredor 42K", "Maratón de Montevideo 2022",
          "Inscripción a la maratón completa", 1100, 450);
      ectrl.altaTipoDeRegistro("Corredor 21K", "Maratón de Montevideo 2022",
          "Inscripción a la media maratón", 900, 750);
      ectrl.altaTipoDeRegistro("Corredor 10K", "Maratón de Montevideo 2022",
          "Inscripción a la carrera 10K", 650, 1400);

      ectrl.altaTipoDeRegistro("General", "Montevideo Comics 2024",
          "Entrada para los 4 días de Montevideo Comics", 600, 1500);
      ectrl.altaTipoDeRegistro("Cosplayer", "Montevideo Comics 2024",
          "Entrada especial con acreditación para concurso cosplay", 300, 50);

      ectrl.altaTipoDeRegistro("General", "Montevideo Comics 2025",
          "Entrada para los 4 días de Montevideo Comics", 800, 1000);
      ectrl.altaTipoDeRegistro("Cosplayer", "Montevideo Comics 2025",
          "Entrada especial con acreditación para concurso cosplay", 500, 100);

      ectrl.altaTipoDeRegistro("General", "Expointer Uruguay 2025",
          "Acceso a la exposición agropecuaria", 300, 5000);
      ectrl.altaTipoDeRegistro("Empresarial", "Expointer Uruguay 2025",
          "Acceso para empresas + networking", 2000, 5);

      ectrl.altaTipoDeRegistro("Full", "Tecnología Punta del Este 2026",
          "Acceso ilimitado + Cena de gala", 1800, 300);
      ectrl.altaTipoDeRegistro("General", "Tecnología Punta del Este 2026", "Acceso general", 1500,
          500);
      ectrl.altaTipoDeRegistro("Estudiante", "Tecnología Punta del Este 2026",
          "Acceso para estudiantes", 1000, 50);

      ectrl.altaTipoDeRegistro("Full", "Mobile World Congress 2025",
          "Acceso ilimitado + Cena de gala", 750, 550);
      ectrl.altaTipoDeRegistro("General", "Mobile World Congress 2025", "Acceso general", 500, 400);
      ectrl.altaTipoDeRegistro("Estudiante", "Mobile World Congress 2025",
          "Acceso para estudiantes", 250, 400);

      ectrl.altaTipoDeRegistro("Full", "Web Summit 2026", "Acceso ilimitado + Cena de gala", 900,
          30);
      ectrl.altaTipoDeRegistro("General", "Web Summit 2026", "Acceso general", 650, 5);
      ectrl.altaTipoDeRegistro("Estudiante", "Web Summit 2026", "Acceso para estudiantes", 300, 1);

      ectrl.altaTipoDeRegistro("Full", "Montevideo Fashion Week 2026",
          "Acceso a todos los eventos de la semana", 450, 50);
      ectrl.altaTipoDeRegistro("Visitante", "Montevideo Fashion Week 2026",
          "Acceso parcial a los eventos de la semana", 150,50);
      
      ectrl.altaTipoDeRegistro("plus50", "Descubre la Magia de Machu Picchu",
              "Viaje para personas con más de 50 años", 250,10);
      
      ectrl.altaTipoDeRegistro("Mayores", "Descubre la Magia de Machu Picchu",
              "Viaje para personas mayores de 18 años", 300,20);
      // ---------------------------------------------- REGISTROS
      // ------------------------------------------
      // altaRegistro(String nicknameAsistente, String nombreEdicion, String
      // nombreTipoRegistro)
      
      
   // RE01
      Asistente a1 = mUs.getAsistente("sofirod");
      Edicion e1 = mEv.getEdicion("Montevideo Rock 2025");
      e1.crearRegistro(a1, "VIP", LocalDate.of(2025, 5, 14));
      a1.getRegistro(e1.getNombre()).setAsistio(true);
      
      // RE02
      Asistente a2 = mUs.getAsistente("sofirod");
      Edicion e2 = mEv.getEdicion("Maratón de Montevideo 2024");
      e2.crearRegistro(a2, "Corredor 21K", LocalDate.of(2024, 7, 30));

      // RE03
      Asistente a3 = mUs.getAsistente("andrearod");
      Edicion e3 = mEv.getEdicion("Web Summit 2026");
      e3.crearRegistro(a3, "Estudiante", LocalDate.of(2025, 8, 21));

      // RE04
      Asistente a4 = mUs.getAsistente("sofirod");
      Edicion e4 = mEv.getEdicion("Maratón de Montevideo 2025");
      e4.crearRegistro(a4, "Corredor 42K", LocalDate.of(2025, 3, 3));
      a4.getRegistro(e4.getNombre()).setAsistio(true);
      
      // RE05
      Asistente a5 = mUs.getAsistente("vale23");
      Edicion e5 = mEv.getEdicion("Mobile World Congress 2025");
      e5.crearRegistro(a5, "Full", LocalDate.of(2025, 8, 22));

      // RE06
      Asistente a6 = mUs.getAsistente("AnaG");
      Edicion e6 = mEv.getEdicion("Maratón de Montevideo 2025");
      e6.crearRegistro(a6, "Corredor 10K", LocalDate.of(2025, 4, 9));
      a6.getRegistro(e6.getNombre()).setAsistio(true);
      
      // RE07
      Asistente a7 = mUs.getAsistente("JaviL");
      Edicion e7 = mEv.getEdicion("Maratón de Montevideo 2025");
      e7.crearRegistro(a7, "Corredor 21K", LocalDate.of(2025, 4, 10));

      // RE08
      Asistente a8 = mUs.getAsistente("MariR");
      Edicion e8 = mEv.getEdicion("Montevideo Comics 2025");
      e8.crearRegistro(a8, "Cosplayer", LocalDate.of(2025, 8, 3));

      // RE09
      Asistente a9 = mUs.getAsistente("SofiM");
      Edicion e9 = mEv.getEdicion("Montevideo Comics 2024");
      e9.crearRegistro(a9, "General", LocalDate.of(2025, 7, 16));
      a9.getRegistro(e9.getNombre()).setAsistio(true);

      // RE12
      Asistente a12 = mUs.getAsistente("MariR");
      Edicion e12 = mEv.getEdicion("Tecnología Punta del Este 2026");
      e12.crearRegistro(a12, "General", LocalDate.of(2025, 10, 1));

   // RE13
      Asistente a13 = mUs.getAsistente("atorres");
      Edicion e13 = mEv.getEdicion("Descubre la Magia de Machu Picchu");
      e13.crearRegistro(a13, "Mayores", LocalDate.of(2025, 10, 6));
      a13.getRegistro(e13.getNombre()).setAsistio(true);
      
   // RE14
      Asistente a14 = mUs.getAsistente("msilva");
      Edicion e14 = mEv.getEdicion("Descubre la Magia de Machu Picchu");
      e14.crearRegistro(a14, "Mayores", LocalDate.of(2025, 8, 10));
       
   // RE15
      Asistente a15 = mUs.getAsistente("AnaG");
      Edicion e15 = mEv.getEdicion("Descubre la Magia de Machu Picchu");
      e15.crearRegistro(a15, "plus50", LocalDate.of(2025, 9, 30));
      a15.getRegistro(e15.getNombre()).setAsistio(true);
      
      // ---------------PATROCINIOS------------------------
   // PAT1
      Institucion ins1 = mIv.obtenerInstitucion("Facultad de Ingeniería");
      Edicion ed1 = mEv.getEdicion("Tecnología Punta del Este 2026");
      TipoRegistro tr1 = ed1.getTipoRegistro("Estudiante");
      ed1.crearPatrocinio(ins1, tr1, NivelPatrocinio.ORO, 20000, 4, LocalDate.of(2025, 8, 21), "TECHUDELAR");

      // PAT2
      Institucion ins2 = mIv.obtenerInstitucion("Agencia Nacional de Investigación e Innovación (ANII)");
      Edicion ed2 = mEv.getEdicion("Tecnología Punta del Este 2026");
      TipoRegistro tr2 = ed2.getTipoRegistro("General");
      ed2.crearPatrocinio(ins2, tr2, NivelPatrocinio.PLATA, 10000, 1, LocalDate.of(2025, 8, 20), "TECHANII");

      // PAT3
      Institucion ins3 = mIv.obtenerInstitucion("Antel");
      Edicion ed3 = mEv.getEdicion("Maratón de Montevideo 2025");
      TipoRegistro tr3 = ed3.getTipoRegistro("Corredor 10K");
      ed3.crearPatrocinio(ins3, tr3, NivelPatrocinio.PLATINO, 25000, 10, LocalDate.of(2025, 3, 4), "CORREANTEL");

      // PAT4
      Institucion ins4 = mIv.obtenerInstitucion("Universidad Católica del Uruguay");
      Edicion ed4 = mEv.getEdicion("Expointer Uruguay 2025");
      TipoRegistro tr4 = ed4.getTipoRegistro("General");
      ed4.crearPatrocinio(ins4, tr4, NivelPatrocinio.BRONCE, 15000, 10, LocalDate.of(2025, 5, 5), "EXPOCAT");
      
      
   // RE10
      Asistente a10 = mUs.getAsistente("msilva");
      Edicion e10 = mEv.getEdicion("Tecnología Punta del Este 2026");
      e10.crearRegistroConCodigo(a10, "Estudiante", "TECHUDELAR", LocalDate.of(2025, 10, 1));
      a10.getRegistro(e10.getNombre()).setCosto(0);
      a10.getRegistro(e10.getNombre()).setPatrocinio(e10.getPatrocinio("Facultad de Ingeniería"));
      
      // RE11
      Asistente a11 = mUs.getAsistente("andrearod");
      Edicion e11 = mEv.getEdicion("Tecnología Punta del Este 2026");
      e11.crearRegistroConCodigo(a11, "General", "TECHANII", LocalDate.of(2025, 10, 6));
      a11.getRegistro(e11.getNombre()).setCosto(0);
      a11.getRegistro(e11.getNombre()).setPatrocinio(e11.getPatrocinio("Agencia Nacional de Investigación e Innovación (ANII)"));
      

      Asistente US01 = mUs.getAsistente("atorres");
      US01.setImg( "IMG-US01.jpg");

      Asistente US03 = mUs.getAsistente("sofirod");
      US03.setImg( "IMG-US03.jpeg");

      Organizador US04 = mUs.getOrganizador("miseventos");
      US04.setImg( "IMG-US04.jpeg");

      Organizador US06 = mUs.getOrganizador("imm");
      US06.setImg( "IMG-US06.png");

      Asistente US07 = mUs.getAsistente("vale23");
      US07.setImg( "IMG-US07.jpeg");

      Asistente US08 = mUs.getAsistente("luciag");
      US08.setImg( "IMG-US08.jpeg");

      Asistente US09 = mUs.getAsistente("andrearod");
      US09.setImg( "IMG-US09.jpeg");

      Organizador US11 = mUs.getOrganizador("mec");
      US11.setImg( "IMG-US11.png");

      Asistente US12 = mUs.getAsistente("AnaG");
      US12.setImg( "IMG-US12.png");

      Asistente US13 = mUs.getAsistente("JaviL");
      US13.setImg( "IMG-US13.jpeg");

      Asistente US14 = mUs.getAsistente("MariR");
      US14.setImg( "IMG-US14.jpeg");

      Asistente US15 = mUs.getAsistente("SofiM");
      US15.setImg( "IMG-US15.jpeg");

      // imagenes eventos
      Evento EV01 = mEv.getEvento("Conferencia de Tecnología");
      EV01.setVisitas(2);
      
      Evento EV02 = mEv.getEvento("Feria del Libro");
      EV02.setImg("IMG-EV02.jpeg");
      EV01.setVisitas(10);

      Evento EV03 = mEv.getEvento("Montevideo Rock");
      EV03.setImg( "IMG-EV03.jpeg");
      EV03.setVisitas(25);

      Evento EV04 = mEv.getEvento("Maratón de Montevideo");
      EV04.setImg( "IMG-EV04.png");
      EV04.setVisitas(13);

      Evento EV05 = mEv.getEvento("Montevideo Comics");
      EV05.setImg( "IMG-EV05.png");
      EV05.setVisitas(5);

      Evento EV06 = mEv.getEvento("Expointer Uruguay");
      EV06.setImg( "IMG-EV06.png");
      EV06.setVisitas(10);
      
      Evento EV07 = mEv.getEvento("Montevideo Fashion Week");
      EV07.setVisitas(8);
      
      Evento EV08 = mEv.getEvento("Global");
      EV08.setImg( "IMG-EV08.jpeg");
      EV08.setVisitas(20);
      // imagenes ediciones

      Edicion EDEV01 = mEv.getEdicion("Montevideo Rock 2025");
      EDEV01.setImg( "IMG-EDEV01.jpeg");
      EDEV01.setVideoURL("https://www.youtube.com/watch?v=YFbRrUX04tU");

      Edicion EDEV02 = mEv.getEdicion("Maratón de Montevideo 2025");
      EDEV02.setImg( "IMG-EDEV02.png");
      EDEV02.setVideoURL("https://www.youtube.com/watch?v=Pg7Jw787MgE");

      Edicion EDEV03 = mEv.getEdicion("Maratón de Montevideo 2024");
      EDEV03.setImg( "IMG-EDEV03.jpeg");
      EDEV03.setVideoURL("https://www.youtube.com/watch?v=hxDn4EEMank");

      Edicion EDEV04 = mEv.getEdicion("Maratón de Montevideo 2022");
      EDEV04.setImg( "IMG-EDEV04.jpeg");

      Edicion EDEV05 = mEv.getEdicion("Montevideo Comics 2024");
      EDEV05.setImg( "IMG-EDEV05.jpeg");
      EDEV05.setVideoURL("https://www.youtube.com/watch?v=4n0itnXxCMg");

      Edicion EDEV06 = mEv.getEdicion("Montevideo Comics 2025");
      EDEV06.setImg( "IMG-EDEV06.jpeg");
      EDEV06.setVideoURL("https://www.youtube.com/watch?v=jRJt4i7G-SY");
      
      Edicion EDEV07 = mEv.getEdicion("Expointer Uruguay 2025");
      EDEV07.setImg( "IMG-EDEV07.jpeg");
      EDEV07.setVideoURL("https://www.youtube.com/watch?v=NFjb-JujCCY");

      Edicion EDEV08 = mEv.getEdicion("Tecnología Punta del Este 2026");
      EDEV08.setImg( "IMG-EDEV08.jpeg");
      EDEV08.setVideoURL("https://www.youtube.com/watch?v=IPukuYb9xWw");

      Edicion EDEV09 = mEv.getEdicion("Mobile World Congress 2025");
      EDEV09.setVideoURL("https://www.youtube.com/watch?v=zNVbgEJfgz8");
      
      Edicion EDEV11 = mEv.getEdicion("Montevideo Fashion Week 2026");
      EDEV11.setImg( "IMG-EDEV11.jpeg");
      
      Edicion EDEV12 = mEv.getEdicion("Descubre la Magia de Machu Picchu");
      EDEV12.setImg( "IMG-EDEV12.jpeg");
      EDEV12.setVideoURL("https://www.youtube.com/watch?v=cnMa-Sm9H4k");
    
      return true;

    }
    catch (Exception e) {
      return false;
    }
  }
}
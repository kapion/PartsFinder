package ru.kapion.servlets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.kapion.model.FBFactory;
import ru.kapion.model.Part;
import ru.kapion.model.PartFB;
import ru.kapion.service.PartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksandr Kuznetsov (kapion) on 27.06.2018
 * Основной сервлет программы
 * - If none of the filter fields is specified then all Parts should be shown.
 * - The table can be sorted by any of the columns by clicking on their header (one click - ASC order, next click - DESC order).
 * - Sorting performed only by one column at a
 * time.
 * - When user uses sorting - filtering is still applied.
 */
@WebServlet("/doAction")
public class MainServlet extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(MainServlet.class);
    private static final String POST = "post";
    private static final String GET  = "get";

    private PartFB filter;
    private List<Part> parts = new ArrayList<>();
    private boolean sortOrderASC = true; // порядок сортировки
    private int previousThIndex = -1;    // предыдущий индекс столбца сортировки
    private boolean firstSort = true;    // признак первой сортировки после выборки данных


    /**
     * POST-Метод для отбора данных с учетом условий фильтра
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        body(POST,request,response);

    }

    /**
     * GET-Метод для сортировки имеющейся ранее выборки List<Part> parts, без запроса к БД
     * @param request
     * @param response
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        body(GET,request,response);
    }


    private void body(String method, HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        LOG.info("Start doAction " + method);
        String errorMessage = "";

        try {
            PartFB bean = FBFactory.initFB(PartFB.class, request);

            if (!bean.isValid()) {
                throw new IllegalArgumentException(bean.getError());
            }

            PartService partService= new PartService();
            if (method.equals(POST)) {
                if (bean.isEmpty()) {
                    //если ни одного фильтра не задано, выводим все
                    parts = partService.getAllParts();
                }else {
                    //если задан фильтр, выбор по параметрам
                    parts = partService.getPartsByFilter(bean);
                }

                //сбрасываем настройки сортировки
                initSort();

                //сохраняем фильтр
                filter = bean;
            }else if(method.equals(GET) && bean.getSortCellIndex() != null) {
                // если передан индекс столбца - сортируем
                int currentThIndex = Integer.valueOf(bean.getSortCellIndex());

                if (firstSort || previousThIndex != currentThIndex) {
                    previousThIndex = currentThIndex;
                    firstSort = false;
                    //при первой сортировке нового столбца - прямой порядок
                    sortOrderASC = true;
                }
                //сортировка имеющейся ранее выборки, без запроса к БД
                sortOrderASC = partService.sortByColumn(parts,currentThIndex,sortOrderASC);
            }

            //результат отбора данных для вывода на форме
            request.setAttribute("parts", partService.correspondingPartToDTO(parts));

            //передаем полученные значения фильтра обратно в форму,
            // чтобы пользователь видел по каким критериям выборка
            request.setAttribute("pn",filter.getPart_number());
            request.setAttribute("pname",filter.getPart_name());
            request.setAttribute("vendor",filter.getVendor());
            request.setAttribute("qty",filter.getQty());
            request.setAttribute("s_from",filter.getShipped_from());
            request.setAttribute("s_to",filter.getShipped_from());
            request.setAttribute("r_from",filter.getReceive_from());
            request.setAttribute("r_to",filter.getReceive_to());

            response.setContentType("text/html;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);
            request.getRequestDispatcher("/index.jsp").include(request,response);


        } catch (IllegalArgumentException e){
            errorMessage = e.getMessage();
            LOG.error("Servlet error: "+errorMessage);
        } catch (Exception e) {
            errorMessage = e.getMessage() != null ? e.getMessage() : "unknown error";
            LOG.error("Unexpected error: "+errorMessage);
        }
        finally
        {
            if(!errorMessage.isEmpty())
            {
                response.setContentType("text/plain charset=utf-8");
                response.getWriter().write(errorMessage);
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

    }

    //начальные настройки сортировки после каждой новой выборки
    private void initSort() {
        sortOrderASC = true;
        previousThIndex = -1;
        firstSort = true;
    }
}

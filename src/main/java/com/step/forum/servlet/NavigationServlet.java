package com.step.forum.servlet;

import com.step.forum.constants.MessageConstants;
import com.step.forum.dao.TopicDaoImpl;
import com.step.forum.model.Topic;
import com.step.forum.service.TopicService;
import com.step.forum.service.TopicServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "NavigationServlet", urlPatterns = "/ns")
public class NavigationServlet extends HttpServlet {

    private TopicService topicService = new TopicServiceImpl(new TopicDaoImpl());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);

    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = null;
        String address = null;

        //action
        if (request.getParameter("action") != null) {
            action = request.getParameter("action");
        } else {
            response.sendRedirect("/");
            return;
        }

        //address
        if (action.equals("newTopic")) {
            address = "/WEB-INF/view/new-topic.jsp";

        } else if (action.equals("topic")) {
            int idTopic = Integer.parseInt(request.getParameter("id"));
            Topic topic = topicService.getTopicById(idTopic);
            topicService.incrementTopicViewCount(idTopic);
            if (topic != null) {
                request.setAttribute("topic", topic);
            }
            address = "/WEB-INF/view/topic.jsp";

        } else if (action.equals("register")) {
            address = "/WEB-INF/view/new-account.jsp";

        } else if (action.equals("login")) {
            address = "/WEB-INF/view/login.jsp";

        } else if (action.equals("logout")) {
            HttpSession session = request.getSession();
            session.removeAttribute("user");
            session.invalidate();
            response.sendRedirect("/");
        }


        if (address != null) {
            request.getRequestDispatcher(address).forward(request, response);
        }
    }





}

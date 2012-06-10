/**
 * Licensed to the Sakai Foundation (SF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The SF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.sakaiproject.nakamura.todo;


import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.request.RequestParameter;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.sakaiproject.nakamura.api.todo.TodoService;
import org.sakaiproject.nakamura.api.todo.TodoItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The <code>TodoServlet</code> provides access to a personal todo list.
 */

@SlingServlet(methods = { "GET", "POST" }, paths = "/api/todo/mine")
public class TodoServlet extends SlingAllMethodsServlet {

  private static final Logger LOGGER = LoggerFactory.getLogger(TodoServlet.class);

  @Reference
  TodoService todoService;

  /**
   * Return JSON for the current user's todo list
   *
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   */
  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
    throws ServletException, IOException {
    LOGGER.info("TodoServlet GET");

    List<TodoItem> myTodos = todoService.getIncompleteItemsForPerson(request.getRemoteUser());

    response.setContentType("application/json");
    response.getWriter().write(makeJson(myTodos));
  }

  @Override
  protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response)
    throws ServletException, IOException {
    LOGGER.info("TodoServlet POST");
    List<TodoItem> todoItemList = new ArrayList<TodoItem>();
    RequestParameter[] todos = request.getRequestParameters("todos");
    if (todos == null) {
      response.sendError(HttpServletResponse.SC_BAD_REQUEST, "todos parameter is required");
      return;
    }
    String personId = request.getRemoteUser();
    for (RequestParameter parameter : todos) {
      String[] fields = parameter.getString().split("\\|");
      todoItemList.add(new TodoItem(Boolean.valueOf(fields[0]), fields[1], fields[2]));
    }

    todoService.saveItemsForPerson(todoItemList, personId);
  }
  
  protected static String makeJson(List<TodoItem> todos) {
    StringBuilder json = new StringBuilder("{\"items\":" + todos.size() + ",\"results\":[");
    String delimiter = "";
    for (TodoItem todo : todos) {
      json.append(delimiter);
      delimiter = ",";
      json.append("{\"completed\": "  + todo.isComplete + ", ");
      json.append("\"description\": \"" + todo.description + "\", ");
      json.append("\"details\": \""     + todo.details + "\"}");
    }
    json.append("]}");
    return json.toString();
  }

}

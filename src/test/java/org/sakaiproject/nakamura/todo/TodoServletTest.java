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

import org.junit.Test;
import org.sakaiproject.nakamura.api.todo.TodoItem;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TodoServletTest {
    private final String SAMPLE_JSON = "{\"items\":2,\"results\":[{\"completed\": false, \"description\": \"Go get the milk\", \"details\": \"Make sure it's not the low fat!\"},{\"completed\": false, \"description\": \"Make dentist appt.\", \"details\": \"Dr. Starch, 555-123-4567\"}]}";

    @Test
    public void testMakeJson() throws Exception {
      TodoItem testItem1 = new TodoItem();
      testItem1.description = "Go get the milk";
      testItem1.details = "Make sure it's not the low fat!";

      TodoItem testItem2 = new TodoItem();
      testItem2.description = "Make dentist appt.";
      testItem2.details = "Dr. Starch, 555-123-4567";
      List<TodoItem> todoItems = Arrays.asList(new TodoItem[]{testItem1, testItem2});
      String json = TodoServlet.makeJson(todoItems);
      assertEquals("Did not get the JSON we expect.", SAMPLE_JSON, json);
    }
}
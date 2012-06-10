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

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Service;
import org.apache.felix.scr.annotations.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sakaiproject.nakamura.api.todo.TodoItem;
import org.sakaiproject.nakamura.api.todo.TodoService;

@Service
@Component
public class BasicTodoService implements TodoService {

 private Map<String, List<TodoItem>> todoStorage;

 @Activate
 public void init() {
   todoStorage = new HashMap<String, List<TodoItem>>();
   todoStorage.put("zach", Arrays.asList(new TodoItem[]{
       new TodoItem(false, "Brush teeth.", "and floss"),
       new TodoItem(false, "Play video games.", "until dawn.")
   }));
 }
 public List<TodoItem> getIncompleteItemsForPerson(String personId) {
   List<TodoItem> todoItems = todoStorage.get(personId);
   if (todoItems == null) {
     todoItems = Arrays.asList(new TodoItem[0]);
   }
   return todoItems;
 }
 
 public void saveItemsForPerson(List<TodoItem> items, String personId) {
     todoStorage.put(personId, items);
 }

}
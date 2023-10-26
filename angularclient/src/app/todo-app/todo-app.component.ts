import { Component } from '@angular/core';
import { Todo } from '../todo';
import { TodoService } from '../todo-service.service';

@Component({
  selector: 'app-todo-app',
  templateUrl: './todo-app.component.html',
  styleUrls: ['./todo-app.component.css']
})
export class TodoAppComponent {

  todos: Todo[];

  constructor (private todoService: TodoService) {
  }

  ngOnInit() {
    this.todoService.findAll().subscribe(data => {
      this.todos = data;
    });
  }

}

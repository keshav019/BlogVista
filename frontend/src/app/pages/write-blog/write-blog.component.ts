import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import Editor from 'ckeditor';
import { environment } from 'src/environments/environment';
import { Topic } from 'src/app/model/topics';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { PostService } from 'src/app/services/post/post.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-write-blog',
  templateUrl: './write-blog.component.html',
  styleUrls: ['./write-blog.component.css'],
})
export class WriteBlogComponent {
  public editorConfig = {
    simpleUpload: {
      uploadUrl: `${environment.apiUrl}/api/v1/file/upload`,
    },
  };
  public Editor = Editor;
  loading = false;
  postData = {
    title: '',
    topics: [] as Topic[],
    content: ''
  };
  constructor(private postService: PostService,private router: Router) { }
  getTopics(topics: Topic[]) {
    this.postData.topics = topics;
  }
  submit() {
    this.loading = true;
    this.postService.addPost(this.postData).subscribe((res) => {
      console.log(res);
      alert("added");
      this.loading = false;
      this.navigateTo();
    }, (err) => {
      this.loading = false;
    })
  }
  navigateTo() {
    this.router.navigate(['/']);
  }
}


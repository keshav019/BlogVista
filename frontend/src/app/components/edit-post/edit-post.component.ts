import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import Editor from 'ckeditor/build/ckeditor';
import { Post } from 'src/app/model/Post';
import { Topic } from 'src/app/model/topics';
import { AuthService } from 'src/app/services/auth/auth.service';
import { PostService } from 'src/app/services/post/post.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-edit-post',
  templateUrl: './edit-post.component.html',
  styleUrls: ['./edit-post.component.css']
})
export class EditPostComponent implements OnInit {
  public editorConfig = {
    simpleUpload: {
      uploadUrl: `${environment.apiUrl}/api/v1/file/upload`,
    },
  };
  public Editor = Editor;
  loading = false;
  post!:Post;
  postId!:string;
  error='';

  constructor(private postService: PostService,private router: Router,private route: ActivatedRoute) { }
  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.postId = params['postId'];
      this.getPost();
    });
  }
  getPost() {
    this.loading = true;
    this.postService.getPostById(this.postId).subscribe(
      (res) => {
        this.post = res;
        this.loading = false;
      },
      (err) => {
        this.error = err;
        this.loading = false;
      }
    );
  }
  getTopics(topics: Topic[]) {
    this.post.topics = topics;
  }
  submit() {
    this.loading = true;
    this.postService.updatePost(this.post).subscribe((res) => {
      console.log(res);
      alert("Updated");
      this.loading = false;
      this.navigateTo();
    }, (err) => {
      this.loading = false;
    })
  }
  navigateTo() {
    this.router.navigate([this.postId]);
  }
}

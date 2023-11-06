import { Component, OnInit } from '@angular/core';
import { Post } from 'src/app/model/Post';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { PostService } from 'src/app/services/post/post.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  posts: Array<Post> = [];
  error = '';
  loading = false;
  constructor(private postService: PostService, private router: Router) { }
  ngOnInit() {
    this.getAllPost();
  }
  getAllPost() {
    this.loading = true;
    this.postService.getAllPost().subscribe((res) => {
      this.posts = res;
      console.log(res);
      this.loading = false;
    }, (err) => {
      this.error = err;
      this.loading = false;
    })
  }
  navigateTo(postId: string) {
    this.router.navigate([postId]);
  }
}

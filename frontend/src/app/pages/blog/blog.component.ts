import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Post } from 'src/app/model/Post';
import { User } from 'src/app/model/user';
import { AuthService } from 'src/app/services/auth/auth.service';
import { PostService } from 'src/app/services/post/post.service';

@Component({
  selector: 'app-blog',
  templateUrl: './blog.component.html',
  styleUrls: ['./blog.component.css']
})
export class BlogComponent implements OnInit {
  postId!: string;
  post!: Post;
  error = '';
  loading = false;
  userData!: User;
  dataSubscription!: Subscription;

  constructor(
    private postService: PostService,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService
  ) { }

  ngOnInit() {
    this.route.params.subscribe((params) => {
      this.postId = params['postId'];
      this.getPost();
    });
    this.dataSubscription = this.authService.user.subscribe((user) => {
      this.userData = user;
    });
  }

  ngOnDestroy(): void {
    if (this.dataSubscription) {
      this.dataSubscription.unsubscribe();
    }
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

  navigateTo(postId: string) {
    this.router.navigate([postId,"edit"]);
  }
  deletePost(postId: string){
    this.loading = true;
    this.postService.deletePost(postId).subscribe(
      (res) => {
        this.loading = false;
        this.router.navigate(["/"]);
      },
      (err) => {
        this.error = err;
        this.loading = false;
      }
    );
  }

}

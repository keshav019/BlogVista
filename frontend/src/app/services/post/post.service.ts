import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Post } from 'src/app/model/Post';
@Injectable({
  providedIn: 'root'
})
export class PostService {

  constructor(private http: HttpClient) { }
  addPost(post: any): Observable<Post> {
    return this.http.post<Post>(`${environment.apiUrl}/api/v1/post`, post);
  }
  getAllPost(): Observable<Post[]> {
    return this.http.get<Post[]>(`${environment.apiUrl}/api/v1/post`);
  }
  getPostById(postId: string): Observable<Post> {
    return this.http.get<Post>(`${environment.apiUrl}/api/v1/post/${postId}`);
  }
  updatePost(post: Post) {
    return this.http.put<Post>(`${environment.apiUrl}/api/v1/post`, post);
  }
  deletePost(postId: string) {
    return this.http.delete<Post>(`${environment.apiUrl}/api/v1/post/${postId}`);
  }
}

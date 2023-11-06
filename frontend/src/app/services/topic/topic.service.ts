import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from 'src/app/model/topics';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TopicService {

  constructor(private http: HttpClient) { }
  getTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(`${environment.apiUrl}/api/v1/topic`)
  }
  addTopics(topicName: string): Observable<Topic> {
    return this.http.post<Topic>(`${environment.apiUrl}/api/v1/topic`, topicName);
  }
}

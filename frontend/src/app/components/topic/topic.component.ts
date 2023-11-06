import { HttpClient } from '@angular/common/http';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Topic } from 'src/app/model/topics';
import { TopicService } from 'src/app/services/topic/topic.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.css']
})
export class TopicComponent implements OnInit {
  @Input() topics:Array<Topic>=[];
  @Output() getTopics = new EventEmitter<Topic[]>();
  loading = false;
  errMessage = '';
  autocompleteValue: string = '';
  options: Array<Topic> = [];
  filteredOptions: Array<Topic> = [];
  showNewTopicInput: boolean = false;
  newTopicName: string = '';
  constructor(private topicService: TopicService) { }
  ngOnInit() {
    this.fetchOptions();
  }

  fetchOptions() {
    this.loading = true;
    this.topicService.getTopics().subscribe((data) => {
      this.options = data;
      this.filteredOptions = data;
    }, (err) => {
      console.log(err);
    }, () => {
      this.loading = false;
    });
  }
  filterOption() {
    this.filteredOptions =
      this.options.filter(x => x.topicName.toLowerCase().startsWith(this.autocompleteValue.toLowerCase()) && !this.topics.includes(x));
  }


  addTopic(topic: Topic) {
    if (topic.topicId == '') {
      this.showNewTopicInput = true;
    } else {
      this.topics.push(topic);
      this.getTopics.emit(this.topics);
    }
    this.autocompleteValue = '';
    this.filterOption();
  }
  removeTopic(topic: Topic) {
    this.topics = this.topics.filter(x => {
      return x.topicId != topic.topicId;
    });
    this.getTopics.emit(this.topics);

  }
  addNewTopic() {
    this.loading = true;
    this.topicService.addTopics(this.newTopicName).subscribe((data) => {
      this.topics.push(data);
      this.getTopics.emit(this.topics);
      this.showNewTopicInput = false;
      this.filterOption();
      this.loading = false;
    }, (err) => {
      console.log(err);
      this.loading = false;
    });
  }

}

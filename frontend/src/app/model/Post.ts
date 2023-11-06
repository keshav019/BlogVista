import { Topic } from "./topics";
import { User } from "./user";

export interface Post {
    postId: string;
    title: string;
    content: string;
    user: User;
    topics: Topic[];
  }
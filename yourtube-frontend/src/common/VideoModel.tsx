export interface VideoModel {
  id: number;
  title: string;
  description: string;
  author: string;
  filename: string;
  tags: string[];
  createdOn: Date;
  encryptedImage: string;
}

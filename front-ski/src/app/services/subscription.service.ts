import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Subscription } from '../models/subscription.model';
@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {

  private baseUrl = 'http://192.168.33.10:8089/api/subscription';

  constructor(private http: HttpClient) { }

  getAll(): Observable<Subscription[]> {
    return this.http.get<Subscription[]>(`${this.baseUrl}/getall`);
  }

  getById(id: number): Observable<Subscription> {
    return this.http.get<Subscription>(`${this.baseUrl}/get/${id}`);
  }

  create(sub: Subscription): Observable<Subscription> {
    return this.http.post<Subscription>(`${this.baseUrl}/add`, sub);
  }

  update(sub: Subscription): Observable<Subscription> {
    return this.http.put<Subscription>(`${this.baseUrl}/update`, sub);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/${id}`);
  }
}
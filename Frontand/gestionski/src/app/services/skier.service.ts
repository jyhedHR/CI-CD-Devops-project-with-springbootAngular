import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Skier } from '../models/skier.model';

@Injectable({
  providedIn: 'root'
})
export class SkierService {
  private baseUrl = 'http://192.168.33.10:8089/api/skier';

  constructor(private http: HttpClient) {}

  getAllSkiers(): Observable<Skier[]> {
    return this.http.get<Skier[]>(`${this.baseUrl}/all`);
  }
  updateSkier(skier: Skier): Observable<Skier> {
    return this.http.put<Skier>(`${this.baseUrl}/update`, skier);
  }

  getSkierById(id: number): Observable<Skier> {
    return this.http.get<Skier>(`${this.baseUrl}/get/${id}`);
  }

  addSkier(skier: Skier): Observable<Skier> {
    return this.http.post<Skier>(`${this.baseUrl}/add`, skier);
  }

  addSkierAndAssignToCourse(skier: Skier, numCourse: number): Observable<Skier> {
    return this.http.post<Skier>(`${this.baseUrl}/addAndAssign/${numCourse}`, skier);
  }

  assignToSubscription(numSkier: number, numSub: number): Observable<Skier> {
    return this.http.put<Skier>(`${this.baseUrl}/assignToSub/${numSkier}/${numSub}`, {});
  }

  assignToPiste(numSkier: number, numPiste: number): Observable<Skier> {
    return this.http.put<Skier>(`${this.baseUrl}/assignToPiste/${numSkier}/${numPiste}`, {});
  }

  deleteSkier(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/delete/${id}`);
  }

  getSkiersBySubscription(type: string): Observable<Skier[]> {
    return this.http.get<Skier[]>(`${this.baseUrl}/getSkiersBySubscription?typeSubscription=${type}`);
  }
}

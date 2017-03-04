//Imports
import { Injectable } from '@angular/core';
import { Http } from '@angular/http';
import { Plant } from './plant';
import { Observable } from "rxjs";


@Injectable()
export class PlantService {
    private plantUrl: string = API_URL + "plant";
    constructor(private http:Http) { }

    // getPlant(): Observable<Plant[]> {
    //     return this.http.request(this.plantUrl).map(res => res.json());
    // }

    getPlantById(id: string): Observable<Plant> {
        return this.http.request(this.plantUrl + "/" + id).map(res => res.json());
    }
}
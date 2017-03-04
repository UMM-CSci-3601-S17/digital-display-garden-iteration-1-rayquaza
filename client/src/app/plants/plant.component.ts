
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {Plant} from './plant';
import {PlantService} from './plant.service';
import 'rxjs/add/operator/switchMap';

@Component({
    selector: 'plant-component',
    template: `
        <p> Hello World!</p>
        <p> {{plant.commonName}}</p>
        <p> {{plant.cultivar}}</p>
        `
})

export class PlantComponent implements OnInit {
    private plant: Plant = {id: "", commonName: "", cultivar: "", gardenLocation: ""};
    constructor(private plantService: PlantService,
                private route: ActivatedRoute,
    ){ }


    ngOnInit() :void{
        this.route.params
            .switchMap((params:Params) => this.plantService.getPlantById(params['id']))
            .subscribe(plant => this.plant = plant);
    }

}

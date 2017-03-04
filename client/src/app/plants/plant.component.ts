
import {Component, OnInit} from '@angular/core';
import {Plant} from './plant';
import {PlantService} from './plant.service';

@Component({
    selector: 'plant-component',
    template: `
        <p> Hello World!</p>
        <p> {{plant.commonName}}</p>
        `
})

export class PlantComponent implements OnInit {
    private plant: Plant = {id: "", commonName: "", cultivar: "", gardenLocation: ""};
    constructor(private plantService: PlantService){
    }


    ngOnInit() :void{
        this.plantService.getPlantById('58b8f2565fbad0fc7a89f747').subscribe(
            plant => {
                this.plant = plant;
            },
            err => {
                console.log(err);
            }
        );
    }

}

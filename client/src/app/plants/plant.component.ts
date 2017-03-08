
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from '@angular/router';
import {Plant} from './plant';
import {PlantService} from './plant.service';
import 'rxjs/add/operator/switchMap';

@Component({
    selector: 'plant-component',
    templateUrl: 'plant.component.html'
})

export class PlantComponent implements OnInit {
    private rated: Boolean = false;
    private commented: Boolean = false;
    private currentQuery: string = "";
    private plant: Plant = {id: "", commonName: "", cultivar: "", gardenLocation: ""};
    constructor(private plantService: PlantService,
                private route: ActivatedRoute,
    ){ }


    ngOnInit() :void{
        this.route.params
            .switchMap((params:Params) => this.plantService.getPlantById(params['id']))
            .subscribe(plant => this.plant = plant);
    }

    private rate(rating: string): void {
        if(!this.rated){
            this.plantService.ratePlant(this.plant["_id"]["$oid"], rating)
                .subscribe(succeeded => this.rated = succeeded);
        }
    }

    private comment(comment: string): void {
        if(!this.commented){
            if(comment != null) {
                this.plantService.commentPlant(this.plant["_id"]["$oid"], comment)
                    .subscribe(succeeded => this.commented = succeeded);
            }
        }
    }
}

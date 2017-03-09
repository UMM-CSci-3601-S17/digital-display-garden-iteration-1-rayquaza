import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { HttpModule, JsonpModule } from '@angular/http';

import { AppComponent }         from './app/app.component';
import { HomeComponent} from './app/home/home.component';
import { routing } from './app/app.routes';
import { FormsModule } from '@angular/forms';
import {PlantComponent} from './app/plants/plant.component';
import {PlantService} from './app/plants/plant.service';


@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        JsonpModule,
        routing,
        FormsModule,
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        PlantComponent
    ],
    providers: [ PlantService ],
    bootstrap: [ AppComponent ]
})

export class AppModule {}

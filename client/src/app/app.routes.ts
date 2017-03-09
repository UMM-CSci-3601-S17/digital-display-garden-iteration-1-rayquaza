// Imports
import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import {PlantComponent} from './plants/plant.component';

// Route Configuration
export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'plant/:id', component: PlantComponent }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);
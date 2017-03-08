// Imports
import { ModuleWithProviders }  from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import {UserListComponent} from "./users/user-list.component";
import {PlantComponent} from './plants/plant.component';
import {AdminComponent} from "./admin/admin.component";

// Route Configuration
export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'users', component: UserListComponent },
    { path: 'plant/:id', component: PlantComponent },
    { path: 'admin', component: AdminComponent }
];

export const routing: ModuleWithProviders = RouterModule.forRoot(routes);
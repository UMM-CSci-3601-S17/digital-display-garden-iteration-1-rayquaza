import { NgModule }       from '@angular/core';
import { BrowserModule }  from '@angular/platform-browser';
import { HttpModule, JsonpModule } from '@angular/http';

import { AppComponent }         from './app/app.component';
import { HomeComponent} from './app/home/home.component';
import { UserListComponent } from './app/users/user-list.component';
import { UserListService } from './app/users/user-list.service';
import { routing } from './app/app.routes';
import { FormsModule } from '@angular/forms';
import {PlantComponent} from './app/plants/plant.component';
import {PlantService} from './app/plants/plant.service';
import {AdminComponent} from './app/admin/admin.component';
import {FileUploadComponent} from './app/admin/file-upload.component';



import { PipeModule } from './pipe.module';

@NgModule({
    imports: [
        BrowserModule,
        HttpModule,
        JsonpModule,
        routing,
        FormsModule,
        PipeModule
    ],
    declarations: [
        AppComponent,
        HomeComponent,
        UserListComponent,
        PlantComponent,
        AdminComponent,
        FileUploadComponent
    ],
    providers: [ UserListService, PlantService ],
    bootstrap: [ AppComponent ]
})

export class AppModule {}

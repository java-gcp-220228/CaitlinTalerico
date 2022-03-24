import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AlertModule  } from './alert';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { EnvironmentPipe } from './environment.pipe';
import { AlertComponent } from './alert/alert.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    EnvironmentPipe,
    AlertComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AlertModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
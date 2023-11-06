import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { MatCardModule } from '@angular/material/card';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { MatStepperModule } from '@angular/material/stepper';
import { ForgotPasswordComponent } from './components/forgot-password/forgot-password.component';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { FooterComponent } from './components/footer/footer.component';
import { MatSelectModule } from '@angular/material/select';
import { ProfileComponent } from './pages/profile/profile.component';
import { BlogPageComponent } from './pages/blog-page/blog-page.component';
import { HomeComponent } from './pages/home/home.component';
import { WriteBlogComponent } from './pages/write-blog/write-blog.component';
import { BlogsComponent } from './pages/blogs/blogs.component';
import { BlogComponent } from './pages/blog/blog.component';
import { SearchResultComponent } from './pages/search-result/search-result.component';
import { ObserversModule } from '@angular/cdk/observers';
import { DrawerComponent } from './components/drawer/drawer.component';
import { SearchDialogComponent } from './components/search-dialog/search-dialog.component';
import { MatDialogModule } from '@angular/material/dialog';
import {MatTooltipModule} from '@angular/material/tooltip';
import {MatGridListModule} from '@angular/material/grid-list';
import { CKEditorModule } from '@ckeditor/ckeditor5-angular';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import { TopicComponent } from './components/topic/topic.component';
import { TokenInterceptorInterceptor } from './interceptor/token-interceptor.interceptor';
import { EditPostComponent } from './components/edit-post/edit-post.component';
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    LoginComponent,
    RegisterComponent,
    ForgotPasswordComponent,
    FooterComponent,
    ProfileComponent,
    BlogPageComponent,
    HomeComponent,
    WriteBlogComponent,
    BlogsComponent,
    BlogComponent,
    SearchResultComponent,
    DrawerComponent,
    SearchDialogComponent,
    TopicComponent,
    EditPostComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    MatFormFieldModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatInputModule,
    MatSidenavModule,
    MatToolbarModule,
    MatFormFieldModule,
    MatListModule,
    MatMenuModule,
    MatCardModule,
    MatStepperModule,
    MatSnackBarModule,
    MatProgressSpinnerModule,
    MatAutocompleteModule,
    MatMenuModule,
    MatSelectModule,
    MatTooltipModule,
    MatGridListModule,
    ObserversModule,
    MatSidenavModule,
    MatDialogModule,
    CKEditorModule
  ],
  providers: [{ 
    provide: HTTP_INTERCEPTORS, useClass: TokenInterceptorInterceptor, multi:true
  }],
  bootstrap: [AppComponent],
})
export class AppModule {}

import { FormControl, ValidationErrors } from "@angular/forms";

export class CustomValidators {
    
    // whitespace validation
    static notOnlyWhitespace(control:FormControl): ValidationErrors | null{
        //check if string only contains whitespace
        if((control.value != null)&&(control.value.trim().length === 0)){
            //invalid, return error object
            return{'notOnlyWhitespace':true} //<- 'notOnlyWhitespace' html template will check this error key
        }else {
            // valid, return null
            return null;
        }
    }
}

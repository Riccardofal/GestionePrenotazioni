package it.riccardofalzea;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class LinguaPrenotazioni implements Serializable {
    
    private static final long serialVersionUID = -4142730404063915531L;
    @Value("${gestioneprenotazioni.istruzioniInglese}")
    String rulesEng;
    @Value("${gestioneprenotazioni.istruzioniItaliano}")
    String rulesIta;
}


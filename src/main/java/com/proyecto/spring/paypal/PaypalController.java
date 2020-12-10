package com.proyecto.spring.paypal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.proyecto.spring.models.entity.Cita;
import com.proyecto.spring.models.entity.Hora;
import com.proyecto.spring.models.entity.Nutricionista;
import com.proyecto.spring.models.entity.Paciente;
import com.proyecto.spring.models.service.ICitaService;
import com.proyecto.spring.models.service.IHoraService;
import com.proyecto.spring.models.service.INutricionistaService;
import com.proyecto.spring.models.service.IPacienteService;
import com.proyecto.spring.util.Utileria;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Slf4j
public class PaypalController {
    
    @Autowired
    public PaypalService service;
    
    public static final String SUCCESS_URL = "paypal/success";
    public static final String CANCEL_URL = "paypal/cancel";
    
    @Value("${server.port}")
    public String servidor;
    
    @Autowired
    public INutricionistaService nutricionistaService;
    
    @Autowired
    public IPacienteService pacienteService;
    
    @Autowired
    public IHoraService horaService;
    
    @Autowired
    public ICitaService citaService;
    
    @PostMapping("paypal/pagarCita")
    @ResponseBody
    public String payment(@RequestParam("idMedico") Long idMedico,
            @RequestParam("fechaCita") String fechaCita,
            @RequestParam("idHora") Long idHora) {
        Order order = null;
        Nutricionista n = null;
        Paciente p = null;
        
        try {
            n = nutricionistaService.getById(idMedico);
            p = pacienteService.ObtenerPorUsuario(UsuarioLogeado());
            
            order = new Order();
            order.setPrice(0.1);
            order.setMethod("Paypal");
            order.setCurrency("USD");
            order.setIntent("sale");
            order.setDescription("Medico: " + n.nomCompletos() + ", Fecha : " + fechaCita);
            String datos = p.getId_Paciente() + ";" + idMedico + ";" + idHora + ";" + fechaCita;
            
            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
                    order.getIntent(), order.getDescription(), "http://localhost:" + servidor + "/" + CANCEL_URL,
                    "http://localhost:" + servidor + "/" + SUCCESS_URL, datos);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return link.getHref();
                }
            }
            
        } catch (PayPalRESTException e) {
            
            e.printStackTrace();
        }
        return "";
    }
    
    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "/views/PagoCancel";
    }
    
    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                // log.info("paymentId : " + paymentId);
                //  log.info("PayerID : " + payerId);
                log.info("payment json : " + payment.toJSON());
                //log.info("Transacion : " + payment.getTransactions().get(0).getDescription().toString());

                String datos[] = payment.getTransactions().get(0).getCustom().toString().split(";");
                
                RegistrarCita(LongConv(datos[0]), LongConv(datos[1]), LongConv(datos[2]), datos[3]);
                return "/views/PagoSuccess";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/";
    }
    
    public String UsuarioLogeado() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        String username = userDetail.getUsername().trim();
        
        return username;
    }
    
    public void RegistrarCita(Long idPaciente, Long idMedico, Long idHora, String fecha) {
        Paciente p = pacienteService.ObtenerPorIdPaciente(idPaciente);
        Nutricionista m = nutricionistaService.getById(idMedico);
        Hora h = horaService.getById(idHora);
        Cita c = new Cita();
        c.setPaciente(p);
        c.setNutricionista(m);
        c.setHora(h);
        c.setFecha_registro(new Date());
        c.setFecha_cita(Utileria.ConvertirFecha(fecha));
        c.setFecha_proxima_cita(null);
        c.setEstado("PENDIENTE");
        citaService.ReservarCita(c);
    }
    
    public Long LongConv(String cadena) {
        try {
            return Long.parseLong(cadena);
        } catch (Exception ex) {
            return 0L;
        }
    }
}

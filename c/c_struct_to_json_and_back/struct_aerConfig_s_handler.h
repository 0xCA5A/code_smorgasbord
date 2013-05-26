#ifndef __STRUCT_AERCONFIG_S_HANDLER_H
#define __STRUCT_AERCONFIG_S_HANDLER_H

#include <stdint.h>


typedef struct aerConfig_s {
  uint32_t   reset_bitfield;
  uint32_t   valid_bitfield;
  int32_t    y2x_delay;
  uint32_t   srate_bitfield;
  int32_t    tail_length;
  int32_t    delay_tx_ag_chg;
  int32_t    delay_rx_ag_chg;
  int32_t    num_samp_interp;
  int32_t    phone_mode;
} aerConfig_t;


// {
//     "reset_bitfield":       0x00000000,
//     "valid_bitfield":       0x00000000,
//     "y2x_delay":            0,
//     "srate_bitfield":       0x00000000,
//     "tail_length":          0,
//     "delay_tx_ag_chg":      0,
//     "delay_rx_ag_chg":      0,
//     "num_samp_interp":      0,
//     "phone_mode":           0
// }

// {
//   "Herausgeber": "Xema",
//   "Nummer": "1234-5678-9012-3456",
//   "Deckung": 2e+6,
//   "Währung": "EURO",
//   "Inhaber": {
//     "Name": "Mustermann",
//     "Vorname": "Max",
//     "männlich": true,
//     "Hobbys": [ "Reiten", "Golfen", "Lesen" ],
//     "Alter": 42,
//     "Kinder": [],
//     "Partner": null
//   }
// }




typedef struct struct_field
{
    char* field_name;
    long field_offset;
} struct_field;


void print_struct_aerConfig_s(struct aerConfig_s* aerConfig);
int set_struct_aerConfig_s_int_data(void* aerConfig, const char* key, int value);

#endif

